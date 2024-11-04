package study.musinsa_project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CartItemsResponse {
    private long productId;
    private int quantity;
    private long userIdx;
    private String message;
}
