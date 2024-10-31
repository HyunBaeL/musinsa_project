package study.musinsa_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.musinsa_project.entity.Users;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    // 레포지토리
    Optional<Users> findByUserName(String userName);
    boolean existsByUserName(String userName);
}
