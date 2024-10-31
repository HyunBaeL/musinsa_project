package study.musinsa_project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ProductDetailResposeDTO {
    private Long id;
    private String name;
    private Integer price;
    private Integer amount;
    private String introduction;
    private List<String> imgs;
    private String username;



}
