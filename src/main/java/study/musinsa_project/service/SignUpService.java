package study.musinsa_project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import study.musinsa_project.dto.SignUp;
import study.musinsa_project.entity.UserStatus;
import study.musinsa_project.entity.Users;
import study.musinsa_project.repository.UsersRepository;

@Service
@RequiredArgsConstructor
public class SignUpService {
    // 회원가입
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public String signUp(SignUp signUpRequest) {
        String userName = signUpRequest.getUserName();
        String password = signUpRequest.getPassword();
        String email = signUpRequest.getEmail();
        String address = signUpRequest.getAddress();
        String phone = signUpRequest.getPhone();
        String referenceId = (signUpRequest.getReferenceId() != null) ? signUpRequest.getReferenceId() : "dummy";
        // default값이 잘 안들어가서 서비스에 추가
        Integer cashes = (signUpRequest.getCashes() != null) ? signUpRequest.getCashes() : 0;
        String profile_img = (signUpRequest.getProfile_img() != null) ? signUpRequest.getProfile_img() : "default_profile.png";

        // 동일한 username이 있다면 회원가입 못하게
        if(usersRepository.existsByUserName(userName)){
            return "동일한 username이 있습니다.";
        }

        String passwordEncoding = passwordEncoder.encode(password);

        // 동일한 username이 없다면 회원가입
        Users registerUser = usersRepository.save(
                Users.builder()
                        .userName(userName)
                        .password(passwordEncoding)
                        .email(email)
                        .address(address)
                        .phone(phone)
                        .referenceId(referenceId)
                        .cashes(cashes)
                        .profile_img(profile_img)
                        .status(UserStatus.Y)
                        .build()
        );
        return "회원가입 성공!";
    }
}
