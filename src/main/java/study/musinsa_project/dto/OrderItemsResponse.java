package study.musinsa_project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class OrderItemsResponse {
    private List<Long> orderItems;
    private long userIdx;
    private String message;
}
