package study.musinsa_project.service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import study.musinsa_project.dto.product.ProductRegisterDto;
import study.musinsa_project.dto.product.ProductSummaryDto;
import study.musinsa_project.entity.*;
import study.musinsa_project.mapper.ProductMapper;
import study.musinsa_project.repository.ProductRepository;
import study.musinsa_project.repository.UsersRepository;
import study.musinsa_project.service.exception.DeletionFailedException;
import study.musinsa_project.service.exception.ExpiredProductException;
import study.musinsa_project.service.exception.NotFoundException;
import study.musinsa_project.service.exception.UnauthorizedActionException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService
{
    private final ProductRepository productRepository;
    private final UsersRepository usersRepository;
    private final ProductMapper productMapper; // mapper 사용


    // 제품등록
    public Long registerProduct(ProductRegisterDto productRegisterDTO)
    {
        updateProductState(); // state 업데이트 호출
        Optional<Users> user = usersRepository.findById(productRegisterDTO.getUserIdx());

        if (user.isPresent())
        {
            Product product = productMapper.toEntity(productRegisterDTO); // DTO 를 엔티티로 변환
            product.setStartDate(LocalDateTime.now()); // 현재 시간 설정
            product.setState(ProductState.Y); // 기본 상태 설정
            product.setUser(user.get()); // 사용자 설정
            productRepository.save(product);
            return product.getId();
        }
        else
            throw new NotFoundException("해당 유저가 존재하지 않습니다.");
    }



    @Transactional // 작업들이 모두 성공해야 최종적으로 데이터가 변경되도록 보장. 하나라도 실패하면 롤백~
    public String deleteItem(Long productId, Long userId) // 상품 삭제
    {
        updateProductState(); // state 업데이트 호출
        Optional<Product> product = productRepository.findById(productId);
        Optional<Users> user = usersRepository.findById(userId);
        if (product.isPresent()) // 상품이 존재하고하는가?
        {
            if (user.isEmpty())
                throw new NotFoundException("해당 유저 ID가 존재하지 않습니다.");


            // 해당 상품의 등록자(user)의 ID와 주어진 userId가 동일한가?
            if (product.get().getUser().getIdx().equals(userId))
            {
                if (product.get().getState() == ProductState.N) // state 가 유효하지 않은 상품인가?
                    // 상태가 'N'인 경우 예외 처리
                    throw new ExpiredProductException("해당 상품은 판매예상기간이 지났습니다.");

                int result = productRepository.updateStateByProductIdAndUserId(ProductState.N, productId, userId);
                if (result > 0)  // 업데이트 된 행의 갯수(result)가 1개 이상이면~? 성공
                    return "상품이 정상적으로 삭제되었습니다.";
                else
                    throw new DeletionFailedException("상품 삭제에 실패했습니다.");
            }
            else
                throw new UnauthorizedActionException("본인이 등록한 상품이 아닙니다.");
        }
        else
            throw new NotFoundException("해당 상품이 존재하지 않습니다.");

    }


    // 유저가 등록한 상품 중 state 가 Y 인 상품만 조회
    public ResponseEntity<List<ProductSummaryDto>> getUserProducts(Long userId)
    {
        updateProductState(); // state 업데이트 호출
        List<Product> products = productRepository.findByUserIdAndState(userId, ProductState.Y);
        List<ProductSummaryDto> summaryDtos = products.stream().map(productMapper::toSummaryDto).collect(Collectors.toList());
        return ResponseEntity.ok(summaryDtos);
    }



    public String updateProductAmount(Long productId, Long userId, int amount)
    {
        updateProductState();
        Optional<Product> product = productRepository.findById(productId);
        Optional<Users> user = usersRepository.findById(userId);

        if (product.isPresent() && user.isPresent())
        {
            if (product.get().getState() == ProductState.N)
                return "해당 상품은 판매예정기한이 지났습니다.";

            // 입력한 수량이 음수인지 체크
            if (amount < 0)
                return "음수는 입력할 수 없습니다.";

            // 재고 업데이트
            product.get().setAmount(amount);
            productRepository.save(product.get());
            return "재고가 성공적으로 업데이트되었습니다.";
        }
        else
            return "찾으시는 상품 또는 해당 유저 ID가 존재하지 않습니다.";
    }


    public ResponseEntity<List<ProductSummaryDto>> getExpiredUserProducts(Long userId)
    {
        updateProductState();
        List<Product> products = productRepository.findByUserIdAndState(userId, ProductState.N);
        List<ProductSummaryDto> summaryDtos = products.stream().map(productMapper::toSummaryDto).collect(Collectors.toList());
        return ResponseEntity.ok(summaryDtos);

    }





    // end_date 가 지난 상품들의 state 를 'N' 으로 변경
    private void updateProductState()
    {
        List<Product> products = productRepository.findAll();
        LocalDateTime now = LocalDateTime.now();

        products.forEach(product -> {
            if (product.getEndDate() != null && product.getEndDate().isBefore(now))
            {
                product.setState(ProductState.N); // 상태를 'N' 으로 변경
                productRepository.save(product); // 변경 사항 저장
            }
        });
    }



}
