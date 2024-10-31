package study.musinsa_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.musinsa_project.entity.Product;
import study.musinsa_project.entity.ProductState;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>
{

    @Modifying
    @Query("update Product p SET p.state = :state WHERE p.id = :productId AND p.user.idx = :userId")
    int updateStateByProductIdAndUserId(@Param("state")ProductState productState, @Param("productId")Long productId, @Param("userId")Long userId);

    @Query("SELECT p FROM Product p WHERE p.user.idx = :userId AND p.state = :state")
    List<Product> findByUserIdAndState(@Param("userId")Long userId, @Param("state")ProductState state );
}
