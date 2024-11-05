package study.musinsa_project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import study.musinsa_project.dto.product.ProductRegisterRequestDto;
import study.musinsa_project.dto.product.ProductRegisterResponseDto;
import study.musinsa_project.dto.product.ProductSummaryDto;
import study.musinsa_project.entity.Product;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    // ProductRegisterDto를 Product 엔티티로 매핑
    @Mapping(target = "id", ignore = true) // id는 무시
    @Mapping(target = "user", ignore = true) // user는 무시
    @Mapping(target = "startDate", ignore = true) // startDate는 무시
    @Mapping(target = "state", ignore = true) // state는 무시
    @Mapping(target = "cartItems", ignore = true) // cartItems는 무시
    Product toEntity(ProductRegisterRequestDto productRegisterRequestDto);

    // product 엔티티를 summaryDto로 매핑
    ProductSummaryDto toSummaryDto(Product product);

    // product 엔티티를 ProductRegisterResponseDto로 매핑
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.itemName", target = "productName")
    @Mapping(target = "message", ignore = true) // message는 무시
    ProductRegisterResponseDto toResponseDto(Product product);
}
