package study.musinsa_project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class MyPageOrdersResponse {

    private String userName;
    private String itemName;
    private int quantity;
    private LocalDateTime createdDate;
}
