package study.musinsa_project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import study.musinsa_project.dto.product.ProductRegisterRequestDto;
import study.musinsa_project.dto.product.ProductRegisterResponseDto;
import study.musinsa_project.dto.product.ProductSummaryDto;
import study.musinsa_project.entity.Product;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    @Mapping(target = "id", ignore = true) // id는 무시
    @Mapping(target = "user", ignore = true) // user는 무시
    @Mapping(target = "startDate", ignore = true) // startDate는 무시
    @Mapping(target = "state", ignore = true) // state는 무시
    @Mapping(target = "cartItems", ignore = true) // cartItems는 무시
    @Mapping(target = "imgs", source = "imgs") // imgs를 변환할 메서드 호출
    Product toEntity(ProductRegisterRequestDto productRegisterRequestDto);


    // List<MultipartFile>을 List<String>으로 변환하는 메서드
    @Value("${spring.cloud.aws.s3.bucket-name}") // 버킷 이름을 필드로 주입
    String bucketName = "";
    default List<String> map(List<MultipartFile> value) {
        if (value == null) {
            return Collections.emptyList();
        }
        return value.stream()
                .map(file -> "https://" + bucketName + ".s3.amazonaws.com/" + file.getOriginalFilename()) // URL 생성
                .collect(Collectors.toList());
    }

    // product 엔티티를 summaryDto로 매핑
    ProductSummaryDto toSummaryDto(Product product);


    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.itemName", target = "productName")
    @Mapping(target = "message", ignore = true) // message는 무시
    ProductRegisterResponseDto toResponseDto(Product product);
}
