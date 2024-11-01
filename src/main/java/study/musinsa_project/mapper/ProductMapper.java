package study.musinsa_project.mapper;

import org.mapstruct.Mapper;
import study.musinsa_project.dto.product.ProductRegisterDto;
import study.musinsa_project.dto.product.ProductSummaryDto;
import study.musinsa_project.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper
{

    // ProductRegisterDto 를 Product 엔티티로 매핑
    Product toEntity(ProductRegisterDto productRegisterDto);

    // product 엔티티를 summaryDto 로 매핑
    ProductSummaryDto toSummaryDto(Product product);
}
