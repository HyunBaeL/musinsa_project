package study.musinsa_project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.musinsa_project.entity.UserStatus;
import study.musinsa_project.entity.Users;
import study.musinsa_project.repository.UsersRepository;

@Service
@RequiredArgsConstructor
public class WithdrawService {
    private final UsersRepository usersRepository;

    public void withdrawUser(String username) {
        Users users = usersRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        users.setStatus(UserStatus.N);
        usersRepository.save(users);
    }
}
