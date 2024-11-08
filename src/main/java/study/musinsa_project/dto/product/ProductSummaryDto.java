package study.musinsa_project.dto.product;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

// 자신이 판매하는 상품 조회 state가 Y인
@Getter
@Setter
public class ProductSummaryDto
{
    private Long id;
    private List<String> imgs;
    private String itemName;
    private int price;
    private int amount;
    private String category;


}
