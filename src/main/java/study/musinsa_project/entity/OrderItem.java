package study.musinsa_project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Entity
@Getter
@Builder
public class OrderItem {


    @Id
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id", insertable = false, updatable = false)
    private Orders orders;

    @Column(name= "orders_id")
    private Long ordersId;

    @OneToOne
    @JsonManagedReference
    @JoinColumn(name = "cartItems_id", insertable = false, updatable = false)
    private CartItems cartItems;

    @Column(name= "cartItems_id")
    private Long cartItemsId;


    public OrderItem() {

    }


}
