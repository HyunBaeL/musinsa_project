package study.musinsa_project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
public class ProductListResponseDTO {
    private long id;
    private String name;
    private int price;
    private String mainImg;
}
