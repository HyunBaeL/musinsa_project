package study.musinsa_project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MyPageUserResponse {
    private long id;
    private String nickName;
}
