package study.musinsa_project.dto.product;


import lombok.Getter;
import lombok.Setter;

// 모든 상품 리턴할때 필요한 값들
@Getter
@Setter
public class ProductSummaryDto
{
    private String itemName;
    private int price;

}