package study.musinsa_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.musinsa_project.entity.Users;

public interface MyPageRepository extends JpaRepository<Users, Integer> {
}
