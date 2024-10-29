
package study.musinsa_project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CartItems {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int quantity;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;


}



