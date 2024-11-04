package study.musinsa_project.repository;

import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.musinsa_project.entity.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
