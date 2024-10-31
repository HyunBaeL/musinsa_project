package study.musinsa_project.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {
    // user Entity
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_idx", nullable = false)
    private Long idx;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "user_password", nullable = false)
    private String password;

    @Column(name = "user_email", nullable = false)
    private String email;

    @Column(name = "reference_id", nullable = true)
    private String referenceId;

    @Column(name = "user_cashes", nullable = true)
    private Integer cashes;

    @Column(name = "user_address", nullable = false)
    private String address;

    @Column(name = "user_phone", nullable = false)
    private String phone;

    @Column(name = "user_profile_img", nullable = true)
    private String profile_img;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_status", nullable = false)
    private UserStatus status;

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Product> products;

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<CartItems> cartItems;

}