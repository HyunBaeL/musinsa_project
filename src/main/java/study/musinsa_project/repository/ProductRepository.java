package study.musinsa_project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.musinsa_project.entity.Product;
import study.musinsa_project.entity.ProductState;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>
{


    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.imgs WHERE p.user.idx = :userId AND p.state = :state")
    List<Product> findByUserIdAndState(@Param("userId")Long userId, @Param("state")ProductState state );


   @Modifying
   @Query("UPDATE Product p SET p.state = 'N' WHERE p.state = 'Y' AND p.endDate < :now")
   void updateExpiredProducts(LocalDateTime now);


    Page<Product> findAllByOrderByIdDesc(Pageable pageable);
    Page<Product> findAllByItemNameContainingOrderByIdDesc(String keyword, Pageable pageable);

}
