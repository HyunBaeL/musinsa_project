package study.musinsa_project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import study.musinsa_project.dto.Login;
import study.musinsa_project.entity.Users;
import study.musinsa_project.repository.UsersRepository;
import study.musinsa_project.config.security.JwtTokenProvider;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UsersRepository usersRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public String login(Login loginRequest) {
        // 사용자의 username으로 사용자 찾기
        Users user = usersRepository.findByUserName(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 비밀번호 검증
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 생성
        return jwtTokenProvider.createToken(user.getUserName()); // username을 기반으로 JWT 생성
    }
}
