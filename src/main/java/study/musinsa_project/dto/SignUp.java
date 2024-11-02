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

    private String referenceId;
    private Integer cashes;
    private String profile_img;

    public SignUp(String userName, String password, String email, String address, String phone, String referenceId, Integer cashes, String profile_img) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.referenceId = referenceId;
        this.cashes = cashes;
        this.profile_img = profile_img;
    }
}
