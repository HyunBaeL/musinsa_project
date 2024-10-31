package study.musinsa_project.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users
{
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

    @Column(name = "user_nickName", nullable = false)
    private String nickName;

    @Column(name = "user_cashes", nullable = false)
    private Integer cashes;

    @Column(name = "user_profileImg", nullable = false)
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