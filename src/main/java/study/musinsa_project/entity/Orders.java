package study.musinsa_project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@Builder
@ToString
public class Orders extends BaseTime{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx", insertable = false, updatable = false)
    private Users user;

    @Column(name = "user_idx")
    private Long userIdx;

    @JsonManagedReference
    @OneToMany(mappedBy = "orders")
    private List<OrderItem> orderItem;


    private boolean state;

    private int totalPrice;

    public Orders() {

    }

    public Orders(long userIdx) {
        this.userIdx = userIdx;
    }

    public Orders(long userIdx, boolean state) {
        this.userIdx = userIdx;
        this.state = state;
    }
}
