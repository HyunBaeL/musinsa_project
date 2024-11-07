package study.musinsa_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.musinsa_project.entity.UserStatus;
import study.musinsa_project.entity.Users;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    // 회원가입
    Optional<Users> findByUserName(String userName);
    Optional<Users> findById(Long idx);
    boolean existsByUserNameAndStatus(String userName, UserStatus status);

}
