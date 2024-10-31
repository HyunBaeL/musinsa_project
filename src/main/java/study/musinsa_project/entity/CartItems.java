
package study.musinsa_project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import study.musinsa_project.dto.CartItemsRequestDTO;
import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class CartItems {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx", insertable = false, updatable = false)
    private Users user;

    @Column(name = "user_idx")
    private Long userIdx;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    @Column(name= "product_id")
    private Long productId;

    public CartItems(Long id,int quantity, Long userIdx, Long productId) {
        this.id = id;
        this.quantity = quantity;
        this.userIdx = userIdx;
        this.productId = productId;
    }

    public CartItems(int quantity, Long userIdx, Long productId) {
        this.quantity = quantity;
        this.userIdx = userIdx;
        this.productId = productId;
    }


    public CartItems() {

    }
}



