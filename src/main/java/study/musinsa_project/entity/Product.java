package study.musinsa_project.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import study.musinsa_project.dto.ProductDetailResposeDTO;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Product
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 상품 고유 ID

    @ManyToOne
    @JoinColumn(name = "user_idx", nullable = false)
    private Users user; // 사용자 (Foreign Key)

    @Column(name = "item_name", nullable = false, length = 100)
    private String itemName; // 상품명

    @Column(name = "price", nullable = false)
    private int price; // 상품 가격

    @Column(name = "amount", nullable = false)
    private int amount; // 상품 재고 수량

    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> imgs; // 상품 이미지 URL

    @Column(name = "introduction", columnDefinition = "TEXT")
    private String introduction; // 상품 설명

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate; // 등록 날짜

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate; // 판매 종료 날짜

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false, columnDefinition = "ENUM('Y', 'N') DEFAULT 'Y'")
    private State state; // 상품 상태


    public enum State {
        Y, N
    }

    @JsonManagedReference
    @OneToMany(mappedBy = "product")
    private List<CartItems> cartItems;

    public ProductDetailResposeDTO getProductDetailResposeDTO(Product product) {
        return ProductDetailResposeDTO.builder()
                .id(product.getId())
                .amount(product.getAmount())
                .price(product.getPrice())
                .name(product.getItemName())
                .introduction(product.getIntroduction())
                .imgs(product.getImgs())
                .username(product.getUser().getUserName())
                .build();
    }
}
