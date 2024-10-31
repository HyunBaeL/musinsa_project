package study.musinsa_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.musinsa_project.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Long>
{
}
