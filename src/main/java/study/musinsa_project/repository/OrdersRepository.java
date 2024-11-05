package study.musinsa_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import study.musinsa_project.dto.MyPageOrdersResponse;
import study.musinsa_project.entity.Orders;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    @Query("select new study.musinsa_project.dto.MyPageOrdersResponse(p.user.userName, p.itemName, ci.quantity, o.createdDate) " +
            "from Orders o " +
            "join CartItems ci on o.userIdx = ci.userIdx " +
            "join ci.product p " +
            "where ci.userIdx = :userId and ci.state = true " +
            "and (p.itemName, o.createdDate) in ( " +
            "    select p2.itemName, min(o2.createdDate) " +
            "    from Orders o2 " +
            "    join o2.orderItem oi2 " +
            "    join CartItems ci2 on o2.userIdx = ci2.userIdx " +
            "    join ci2.product p2 " +
            "    where ci2.userIdx = :userId and ci2.state = true " +
            "    group by p2.itemName " +
            ")")
    List<MyPageOrdersResponse> selectMyPageOrders(Long userId);

}
