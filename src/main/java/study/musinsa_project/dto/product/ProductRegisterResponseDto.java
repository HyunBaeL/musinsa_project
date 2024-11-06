package study.musinsa_project.dto.product;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
// 등록 후 사용자에게 응답하는 dto
public class ProductRegisterResponseDto
{
    private Long productId;
    private String productName;
    private int price;
    private int amount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String category;
    private List<String> imgs;
    private String message; // 등록완료 메시지

}
