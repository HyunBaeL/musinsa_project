package study.musinsa_project.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.*;
import study.musinsa_project.dto.MyPageUserDetailResponse;
import study.musinsa_project.dto.MyPageUserResponse;
import study.musinsa_project.dto.MyPageUserUpdateRequest;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

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

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Orders> orders;

    /** Users 엔티티 -> MyPageUserResponseToDto DTO 변환
     * @return = MyPageUserResponseToDto DTO
     */
    public MyPageUserResponse MyPageUserResponseToDto(){
        return MyPageUserResponse.builder()
                .userId(this.idx)
                .nickName(this.userName)
                .build();
    }

    /** Users 엔티티 -> MyPageUserDetailResponse DTO 변환
     * @return = MyPageUserDetailResponse DTO
     */
    public MyPageUserDetailResponse MyPageUserDetailResponseToDto() {
        return MyPageUserDetailResponse.builder()
                .userId(this.idx)
                .userName(this.userName)
                .email(this.email)
                .referenceId(this.referenceId)
                .caches(this.cashes)
                .address(this.address)
                .phone(this.phone)
                .profile_img(this.profile_img)
                .build();
    }

    /** 마이페이지 유저 정보 수정
     * @param myPageUserUpdateRequest = 수정된 회원 정보
     */
    public void userUpdate(MyPageUserUpdateRequest myPageUserUpdateRequest){
        userName = myPageUserUpdateRequest.getUserName();
        email = myPageUserUpdateRequest.getEmail();
        referenceId = myPageUserUpdateRequest.getReferenceId();
        cashes = myPageUserUpdateRequest.getCaches();
        address = myPageUserUpdateRequest.getAddress();
        phone = myPageUserUpdateRequest.getPhone();
    }

}