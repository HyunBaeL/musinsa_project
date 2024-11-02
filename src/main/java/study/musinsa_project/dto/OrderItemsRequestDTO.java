package study.musinsa_project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import study.musinsa_project.entity.OrderItem;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
public class OrderItemsRequestDTO {

    private List<Long> orderItems;
    private long userIdx;
}

