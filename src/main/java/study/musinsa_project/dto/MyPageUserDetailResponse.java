package study.musinsa_project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MyPageUserDetailResponse {

    private Long userId;
    private String userName;
    private String email;
    private String referenceId;
    private Integer caches;
    private String address;
    private String phone;
    private String profile_img;

}
