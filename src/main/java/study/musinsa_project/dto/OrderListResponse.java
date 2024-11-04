package study.musinsa_project.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class OrderListResponse {
    private String userName;
    private String userAddress;
    private String userPhone;
    private int totalPrice;
    private List<ProductListResponseDTO> itemList;

}
