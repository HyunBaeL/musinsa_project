package study.musinsa_project.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import study.musinsa_project.config.security.JwtTokenProvider;
import study.musinsa_project.dto.Login;
import study.musinsa_project.entity.Users;
import study.musinsa_project.repository.UsersRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UsersRepository usersRepository;
    @Transactional
    public String login(Login loginRequest) {
        Optional<Users> optionalUser = usersRepository.findByUserName(loginRequest.getUsername());

        // 사용자를 찾지 못했거나 비밀번호가 일치하지 않을 경우
        if (optionalUser.isEmpty() || !optionalUser.get().getPassword().equals(loginRequest.getPassword())) {
            throw new RuntimeException("잘못된 username 또는 password 입니다.");
        }

        // 로그인 성공 시 반환할 토큰이나 메시지 처리 (JWT, 세션 등)
        // 나중에 JWT 구현 시 여기에 추가하면 됩니다.

        return "로그인 성공!";
    }

//    private final AuthenticationManager authenticationManager;
//
//    private final JwtTokenProvider jwtTokenProvider;
//
//    public String login(Login loginRequest) {
//        String username = loginRequest.getUsername();
//        String password = loginRequest.getPassword();
//
//        Optional<Users> usersOptional = usersRepository.findByUserName(username);
//        if (usersOptional.isEmpty()) {
//            throw new UsernameNotFoundException("Username을 찾을 수 없습니다.");
//        }
//        Users users = usersOptional.get();
//        if (!users.getPassword().equals(password)) {
//            throw new RuntimeException("잘못되었거나 존재하지 않는 password입니다");
//        }
//
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(username, password)
//            );
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            return jwtTokenProvider.createToken(username);
//        } catch (Exception e) {
//            throw new RuntimeException("잘못된 username, password 입니다.");
//        }
//    }
}
