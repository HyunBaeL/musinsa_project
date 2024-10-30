package study.musinsa_project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class MyPageCartDetailResponse {

    private Long id;
    private String itemName;
    private int price;
    private int amount;
    private String imgs;
    private String introduction;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
