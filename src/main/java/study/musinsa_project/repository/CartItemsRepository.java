package study.musinsa_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import study.musinsa_project.entity.CartItems;

import java.util.List;
import java.util.Optional;

public interface CartItemsRepository extends JpaRepository<CartItems, Long> {

    @Query("select c from CartItems c where c.user.idx = :userId")
    List<CartItems> selectUserId(int userId);

    @Query("select c from CartItems c where c.user.idx = :userId and c.state = true")
    List<CartItems> selectCartItems(int userId);


    Optional<CartItems> findByUserIdxAndProductIdAndState(long userIdx, long productId, boolean b);
}
