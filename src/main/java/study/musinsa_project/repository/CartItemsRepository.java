package study.musinsa_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import study.musinsa_project.entity.CartItems;

import java.util.List;

public interface CartItemsRepository extends JpaRepository<CartItems, Integer> {

    @Query("select c from CartItems c where c.user.idx = :userId")
    List<CartItems> selectUserId(int userId);
}
