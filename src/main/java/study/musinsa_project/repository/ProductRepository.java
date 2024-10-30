package study.musinsa_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.musinsa_project.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
