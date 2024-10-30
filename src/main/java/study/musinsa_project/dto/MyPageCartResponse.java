package study.musinsa_project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import study.musinsa_project.entity.CartItems;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class MyPageCartResponse {

    private List<CartItems> cartItems;
    private List<MyPageCartDetailResponse> products;
}
