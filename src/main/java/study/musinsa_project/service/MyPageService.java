package study.musinsa_project.service;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import study.musinsa_project.dto.MyPageCartDetailResponse;
import study.musinsa_project.dto.MyPageCartResponse;
import study.musinsa_project.dto.MyPageUserResponse;
import study.musinsa_project.entity.CartItems;
import study.musinsa_project.entity.Product;
import study.musinsa_project.entity.Users;
import study.musinsa_project.exception.mypage.CommonError;
import study.musinsa_project.exception.mypage.MyPageException;
import study.musinsa_project.repository.CartItemsRepository;
import study.musinsa_project.repository.MyPageRepository;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyPageService {

    private final MyPageRepository myPageRepository;
    private final CartItemsRepository cartItemsRepository;
    private final S3Client s3Client;
    private final String s3BucketName;

    public MyPageUserResponse selectMyPage(int userId) {
        Users user = myPageRepository.findById(userId).orElseThrow(() ->
                new MyPageException(CommonError.USER_NOT_FOUND,CommonError.USER_NOT_FOUND.getMessage()));

        return MyPageUserResponse.builder()
                .id(user.getIdx())
                .nickName(user.getUserName())
                .build();
    }

    public MyPageCartResponse selectMyPageCart(int userId) {
        List<CartItems> cartItems = cartItemsRepository.selectUserId(userId);
        List<MyPageCartDetailResponse> productDetails = new ArrayList<>();

        for (CartItems cartItem : cartItems) {
            Product product = cartItem.getProduct();
            MyPageCartDetailResponse productDetail = MyPageCartDetailResponse.builder()
                    .id(product.getId())
                    .itemName(product.getItemName())
                    .price(product.getPrice())
                    .amount(product.getAmount())
                    .imgs(product.getImgs().get(0))
                    .introduction(product.getIntroduction())
                    .startDate(product.getStartDate())
                    .endDate(product.getEndDate())
                    .build();

            productDetails.add(productDetail);
        }

        return MyPageCartResponse.builder()
                .cartItems(cartItems)
                .products(productDetails)
                .build();
    }

    public String upload(MultipartFile image, int userId) {
        // 입력받은 이미지 파일이 빈 파일인지 검증
        if(image.isEmpty() || Objects.isNull(image.getOriginalFilename())) {
            throw new MyPageException(CommonError.FILE_NOT_FOUND,CommonError.FILE_NOT_FOUND.getMessage());
        }
        return this.uploadImage(image, userId);
    }

    private String uploadImage(MultipartFile image, int userId) {
        this.validateImageFileExtension(Objects.requireNonNull(image.getOriginalFilename()));
        try {
            return this.uploadImageToS3(image, userId);
        } catch (Exception e) {
            throw new MyPageException(CommonError.BAD_REQUEST,CommonError.BAD_REQUEST.getMessage());
        }
    }

    private void validateImageFileExtension(String filename) {
        int lastDotIndex = filename.lastIndexOf(".");
        if (lastDotIndex == -1) {
            throw new MyPageException(CommonError.INVALID_FILE_EXTENSION,CommonError.INVALID_FILE_EXTENSION.getMessage());
        }

        String extension = filename.substring(lastDotIndex + 1).toLowerCase();
        List<String> allowedExtensionList = Arrays.asList("jpg", "jpeg", "png", "gif");

        if (!allowedExtensionList.contains(extension)) {
            throw new MyPageException(CommonError.INVALID_FILE_EXTENSION,CommonError.INVALID_FILE_EXTENSION.getMessage());
        }
    }

    private String uploadImageToS3(MultipartFile image, int userId) throws IOException {
        String originalFilename = image.getOriginalFilename(); // 원본 파일 명
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1); // 확장자 명
        String s3FileName = UUID.randomUUID().toString().substring(0, 10) + originalFilename; // 변경된 파일명

        InputStream inputStream = image.getInputStream();

        try {
            // S3로 요청할 때 사용할 PutObjectRequest 생성
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(s3BucketName)
                    .key(s3FileName)
                    .contentType("image/" + extension)
                    .build();

            // S3에 이미지 데이터를 업로드
            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, image.getSize()));

        } catch (Exception e) {
            throw new MyPageException(CommonError.SERVICE_UNAVAILABLE,CommonError.SERVICE_UNAVAILABLE.getMessage());
        } finally {
            inputStream.close();
        }

        return s3Client.utilities().getUrl(b -> b.bucket(s3BucketName).key(s3FileName)).toExternalForm();
    }
}
