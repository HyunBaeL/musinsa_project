package study.musinsa_project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import study.musinsa_project.dto.product.ProductRegisterRequestDto;
import study.musinsa_project.dto.product.ProductRegisterResponseDto;
import study.musinsa_project.dto.product.ProductSummaryDto;
import study.musinsa_project.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper
{

    // ProductRegisterDto 를 Product 엔티티로 매핑
    Product toEntity(ProductRegisterRequestDto productRegisterRequestDto);

    // product 엔티티를 summaryDto 로 매핑
    ProductSummaryDto toSummaryDto(Product product);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.itemName", target = "productName")
    ProductRegisterResponseDto toResponseDto(Product product);
}
