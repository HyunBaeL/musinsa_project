package study.musinsa_project.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignUp {
    // 회원가입
    @NotNull(message = "userName은 필수")
    private String userName;
    @NotNull(message = "password는 필수")
    private String password;
    @NotNull(message = "email은 필수")
    private String email;
    @NotNull(message = "address는 필수")
    private String address;
    @NotNull(message = "phone은 필수")
    private String phone;

    private String referenceId = "dummy";
    private Integer cashes = 0;
    private String profile_img = "default_profile.png";

    public SignUp(String userName, String password, String email, String address, String phone, String referenceId, Integer cashes, String profile_img) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.referenceId = (referenceId != null) ? referenceId : "dummy";
        this.cashes = (cashes != null) ? cashes : 0;
        this.profile_img = (profile_img != null) ? profile_img : "default_profile.png";
    }
}
