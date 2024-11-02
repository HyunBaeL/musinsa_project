package study.musinsa_project.dto.product;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProductDetailDto
{
    private String itemName;
    private int price;
    private int amount;
    private String introduction;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")

    private LocalDateTime endDate;
    private String category;
    private String state;
    // 필요한 경우, 이미지 필드는 나중에 추가 예정


}
