package study.musinsa_project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import study.musinsa_project.dto.MyPageCartDetailResponse;
import study.musinsa_project.dto.MyPageCartResponse;
import study.musinsa_project.dto.MyPageUserResponse;
import study.musinsa_project.entity.CartItems;
import study.musinsa_project.entity.Product;
import study.musinsa_project.entity.Users;
import study.musinsa_project.repository.CartItemsRepository;
import study.musinsa_project.repository.MyPageRepository;
import study.musinsa_project.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyPageService {

    private final MyPageRepository myPageRepository;
    private final CartItemsRepository cartItemsRepository;
    private final ProductRepository productRepository;

    public MyPageUserResponse selectMyPage(int userId) {

        Users user = myPageRepository.findById(userId).orElseThrow(IllegalArgumentException::new);

        return MyPageUserResponse.builder()
                .id(user.getIdx())
                .nickName(user.getNickName())
                .build();
    }

    public MyPageCartResponse selectMyPageCart(int userId) {
        List<CartItems> cartItems = cartItemsRepository.selectUserId(userId);
        log.info(cartItems.toString());

        List<MyPageCartDetailResponse> productDetails = new ArrayList<>();

        for (CartItems cartItem : cartItems) {
            Product product = cartItem.getProduct();
            MyPageCartDetailResponse productDetail = MyPageCartDetailResponse.builder()
                    .id(product.getId())
                    .itemName(product.getItemName())
                    .price(product.getPrice())
                    .amount(product.getAmount())
                    .imgs(product.getImgs().get(0))
                    .introduction(product.getIntroduction())
                    .startDate(product.getStartDate())
                    .endDate(product.getEndDate())
                    .build();

            productDetails.add(productDetail);
        }

        return MyPageCartResponse.builder()
                .cartItems(cartItems)
                .products(productDetails)
                .build();
    }

}
