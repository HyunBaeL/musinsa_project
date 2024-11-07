package study.musinsa_project.service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import study.musinsa_project.dto.product.ProductRegisterRequestDto;
import study.musinsa_project.dto.product.ProductRegisterResponseDto;
import study.musinsa_project.dto.product.ProductSummaryDto;
import study.musinsa_project.entity.*;
import study.musinsa_project.exception.mypage.CommonError;
import study.musinsa_project.exception.mypage.MyPageException;
import study.musinsa_project.mapper.ProductMapper;
import study.musinsa_project.repository.ProductRepository;
import study.musinsa_project.repository.UsersRepository;
import study.musinsa_project.service.exception.*;
import study.musinsa_project.dto.ProductDetailResposeDTO;
import study.musinsa_project.dto.ProductListResponseDTO;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class ProductService
{
    private final ProductRepository productRepository;
    private final UsersRepository usersRepository;
    private final ProductMapper productMapper; // mapper 사용
    private final S3Client s3Client;
    private final String s3BucketName;


    @Scheduled(cron = "1 0 0 * * *", zone = "Asia/Seoul") // 매 00시00분01초에 실행
    @Transactional
    public void updateProductStateScheduler() {
        updateProductState(); // 만료된 상품 상태 업데이트
    }

        // 제품등록
    public ProductRegisterResponseDto registerProduct(ProductRegisterRequestDto productRegisterRequestDTO) {
        Optional<Users> user = usersRepository.findById(productRegisterRequestDTO.getUserIdx());

        if (user.isPresent()) {
            Product product = productMapper.toEntity(productRegisterRequestDTO); // DTO 를 엔티티로 변환
            product.setStartDate(LocalDateTime.now().plusHours(9)); // 현재 시간 설정
            product.setState(ProductState.Y); // 기본 상태 설정
            product.setUser(user.get()); // 사용자 설정


            List<String> imageUrls = uploadImagesToS3(productRegisterRequestDTO.getImgs());
            product.setImgs(imageUrls); // S3에 업로드된 이미지 URL 저장

            productRepository.save(product);

            // 응답 dto 생성
            ProductRegisterResponseDto responseDto = productMapper.toResponseDto(product);
            responseDto.setMessage("성공적으로 상품이 등록됐습니다.");
            responseDto.setImgs(imageUrls);
            return responseDto;
        } else {
            throw new NotFoundException("해당 유저가 존재하지 않습니다.");
        }
    }

    // 이미지 파일을 S3에 업로드하고 URL 리스트 반환
    private List<String> uploadImagesToS3(List<MultipartFile> files) {
        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile file : files) {
            String imageUrl = uploadImageToS3(file);
            imageUrls.add(imageUrl);
        }
        return imageUrls;
    }

    // 단일 이미지 파일을 S3에 업로드하고 URL 반환
    private String uploadImageToS3(MultipartFile file) {
        String originalFilename = file.getOriginalFilename(); // 원본 파일 명
        String s3FileName = UUID.randomUUID().toString().substring(0, 10) + originalFilename; // 변경된 파일명
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1); // 확장자 명


        try {
            InputStream inputStream = file.getInputStream();

            // S3에 업로드
            PutObjectRequest putObjectRequest =
                    PutObjectRequest.builder()
                            .bucket(s3BucketName)
                            .key(s3FileName)
                            .contentType("image/" + extension)
                            .build();


            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, file.getSize()));

            // 업로드된 파일의 URL 생성
            return s3Client.utilities().getUrl(b -> b.bucket(s3BucketName).key(s3FileName)).toExternalForm();
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드에 실패했습니다.", e);
        }
    }


    @Transactional // 작업들이 모두 성공해야 최종적으로 데이터가 변경되도록 보장.
    public String deleteItem(Long productId, Long userId) // 상품 삭제
    {
        Optional<Product> product = productRepository.findById(productId);
        Optional<Users> user = usersRepository.findById(userId);

        if (product.isPresent()) // 상품이 존재하는가?
        {
            if (user.isEmpty())
                throw new NotFoundException("해당 유저 ID가 존재하지 않습니다.");


            // 해당 상품의 등록자(user)의 ID와 주어진 userId가 동일한가?
            if (product.get().getUser().getIdx().equals(userId))
            {
                if (product.get().getState() == ProductState.N) // state 가 유효하지 않은 상품인가?
                    // 상태가 'N'인 경우 예외 처리
                    throw new ExpiredProductException("해당 상품은 구매불가 상태입니다.");

                int result = productRepository.updateStateByProductIdAndUserId(productId, userId);
                if (result > 0)  // 업데이트 된 행의 갯수(result)가 1개 이상이면?
                    return "상품이 정상적으로 삭제되었습니다.";

                throw new DeletionFailedException("상품 삭제에 실패했습니다.");
            }
            throw new UnauthorizedActionException("본인이 등록한 상품이 아닙니다.");
        }
        throw new NotFoundException("해당 상품이 존재하지 않습니다.");
    }





    // 유저가 등록한 상품 중 state 가 Y 인 상품만 조회
    public List<ProductSummaryDto> getUserProducts(Long userId)
    {
        List<Product> products = productRepository.findByUserIdAndState(userId, ProductState.Y);
        return products.stream().map(productMapper::toSummaryDto).collect(Collectors.toList());
    }


    // 본인이 등록한 상품의 수량을 조정
    public String updateProductAmount(Long productId, Long userId, int amount)
    {
        Optional<Product> product = productRepository.findById(productId);
        Optional<Users> user = usersRepository.findById(userId);

        if (product.isPresent() && user.isPresent())
        {
            if (product.get().getState() == ProductState.N)
                return "해당 상품은 판매기한 만료 및 삭제처리 되었습니다.";

            if (!product.get().getUser().getIdx().equals(userId))
                return "본인이 등록한 상품이 아닙니다.";

            // 입력한 수량이 음수인지 체크
            if (amount < 0)
                return "음수는 입력할 수 없습니다.";
            else if (amount == product.get().getAmount())
                return "현재 상품재고 수량(" + product.get().getAmount() + "개" + ")과 바꾸려는 재고 수량값(" + amount + "개" + ")이 동일합니다.";

            // 재고 업데이트
            product.get().setAmount(amount);
            productRepository.save(product.get());
            return "재고가 성공적으로 업데이트되었습니다(" + amount + "개" + ")";
        }
        else
            return "찾으시는 상품 또는 해당 유저 ID가 존재하지 않습니다.";
    }

    // 본인이 등록한 상품 중 더 이상 판매하지 않는 상품을 모두 조회
    public List<ProductSummaryDto> getExpiredUserProducts(Long userId)
    {
        List<Product> products = productRepository.findByUserIdAndState(userId, ProductState.N);
        return products.stream().map(productMapper::toSummaryDto).collect(Collectors.toList());

    }

    public ProductDetailResposeDTO getProductById(Long productId) {
        return productRepository.findById(productId)
                .map(item -> item.getProductDetailResposeDTO(item))
                .orElseThrow(() -> new MyPageException(CommonError.PRODUCT_NOT_FOUND, CommonError.PRODUCT_NOT_FOUND.getMessage()));

    }


    public Page<ProductListResponseDTO> getProductAll(String keyword, Pageable pageable) {


        Page<Product> products;

        if (keyword == null || keyword.isEmpty()) {
            products = productRepository.findAllByOrderByIdDesc(pageable);
        } else {
            products = productRepository.findAllByItemNameContainingOrderByIdDesc(keyword, pageable);
        }

        if (products.isEmpty()) {
            throw new MyPageException(CommonError.PRODUCT_NOT_FOUND, CommonError.PRODUCT_NOT_FOUND.getMessage());
        }

        Page<ProductListResponseDTO> productListResponseDTOS = products
                .map(product -> ProductListResponseDTO.builder()
                        .productId(product.getId())
                        .price(product.getPrice())
                        .name(product.getItemName())
                        .mainImg(product.getImgs().get(0))
                        .username(product.getUser().getUserName())
                        .amount(product.getAmount())
                        .build());



        return productListResponseDTOS;
    }



    // end_date 가 지난 상품들의 state 를 'N' 으로 변경
    @Transactional
    public void updateProductState()
    {
        productRepository.updateExpiredProducts(LocalDateTime.now()); // 판매예정시간이 지난 상품은 N으로 변경 스케쥴러 이용
    }

}
