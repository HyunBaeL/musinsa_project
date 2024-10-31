package study.musinsa_project.Service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Map<String, String> users = new HashMap<>();

    static {
        // 테스트를 위한 사용자 추가 (username: user, password: password)
        users.put("user", "password");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String password = users.get(username);

        if (password == null) {
            throw new UsernameNotFoundException("패스워드가 틀렸습니다.");
        }

        return User.withUsername(username)
                .password(password)
                .roles("USER")
                .build();
    }
}
