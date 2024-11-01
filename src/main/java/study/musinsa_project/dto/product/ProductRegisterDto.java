package study.musinsa_project.dto.product;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
// 상품 등록할때 사용할 Dto
public class ProductRegisterDto
{
    private Long userIdx;
    private String itemName;
    private int price;
    private int amount;
    private String introduction;
    private LocalDateTime endDate; // 판매 종료 날짜
    private String category; // 카테고리 (상의, 하의)
}