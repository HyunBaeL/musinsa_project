package study.musinsa_project.dto.product;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
// 상품 등록할때 사용할 Dto
public class ProductRegisterRequestDto
{
    private Long userIdx;
    private List<MultipartFile> imgs;
    private String itemName;
    private int price;
    private int amount;
    private String introduction;
    private LocalDateTime endDate; // 판매 종료 날짜
    private String category; // 카테고리 (상의, 하의)

}
