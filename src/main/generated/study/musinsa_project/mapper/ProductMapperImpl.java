package study.musinsa_project.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import study.musinsa_project.dto.product.ProductRegisterDto;
import study.musinsa_project.dto.product.ProductSummaryDto;
import study.musinsa_project.entity.Product;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-01T14:58:48+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.13 (OpenLogic)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product toEntity(ProductRegisterDto productRegisterDto) {
        if ( productRegisterDto == null ) {
            return null;
        }

        Product product = new Product();

        product.setItemName( productRegisterDto.getItemName() );
        product.setPrice( productRegisterDto.getPrice() );
        product.setAmount( productRegisterDto.getAmount() );
        product.setIntroduction( productRegisterDto.getIntroduction() );
        product.setEndDate( productRegisterDto.getEndDate() );

        return product;
    }

    @Override
    public ProductSummaryDto toSummaryDto(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductSummaryDto productSummaryDto = new ProductSummaryDto();

        productSummaryDto.setItemName( product.getItemName() );
        productSummaryDto.setPrice( product.getPrice() );

        return productSummaryDto;
    }
}
