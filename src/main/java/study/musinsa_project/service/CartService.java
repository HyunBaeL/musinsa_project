package study.musinsa_project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import study.musinsa_project.dto.*;
import study.musinsa_project.entity.*;
import study.musinsa_project.exception.mypage.CommonError;
import study.musinsa_project.exception.mypage.MyPageException;
import study.musinsa_project.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemsRepository cartItemsRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrdersRepository ordersRepository;
    private final ProductRepository productRepository;
    private final UsersRepository usersRepository;

    public List<ProductListResponseDTO> getCartItemsByUserId(Integer userId) {
        List<CartItems> itemList = cartItemsRepository.selectCartItems(userId);
        if(itemList.isEmpty()){
            throw new MyPageException(CommonError.CART_ITEMS_NOT_FOUND,CommonError.CART_ITEMS_NOT_FOUND.getMessage());
        }

        return itemList.stream().map(item -> ProductListResponseDTO.builder()
                        .productId(item.getProductId())
                        .name(item.getProduct().getItemName())
                        .price(item.getProduct().getPrice())
                        .username(item.getProduct().getUser().getUserName())
                        .mainImg(item.getProduct().getImgs().get(0))
                        .amount(item.getQuantity())
                        .cartItemId(item.getId())
                        .build()).collect(Collectors.toList());

    }


    public CartItemsResponse createCartItem(CartItemsRequestDTO cartItemsRequestDTO) {

        int amount = productRepository.findById(cartItemsRequestDTO.getProductId()).orElseThrow(
                () -> new MyPageException(CommonError.CART_ITEMS_NOT_FOUND,CommonError.CART_ITEMS_NOT_FOUND.getMessage())).getAmount();

        if(amount < cartItemsRequestDTO.getQuantity()){
            throw new MyPageException(CommonError.CART_ITEMS_NOT_ADDED, CommonError.CART_ITEMS_NOT_ADDED.getMessage()+ String.format("(현재 남은 수량 : %d 개)",amount));
        }

        CartItems item =cartItemsRepository.save(new CartItems(cartItemsRequestDTO.getQuantity(),cartItemsRequestDTO.getUserIdx(),cartItemsRequestDTO.getProductId()));

        return CartItemsResponse.builder()
                .message("상품이 장바구니에 추가되었습니다.")
                .quantity(cartItemsRequestDTO.getQuantity())
                .userIdx(cartItemsRequestDTO.getUserIdx())
                .productId(cartItemsRequestDTO.getProductId())
                .build();
    }

    public CartItemsResponse deleteCartItem(Long cartItemId) {
        CartItems item = cartItemsRepository.findById(cartItemId).orElseThrow(
                () -> new MyPageException(CommonError.PRODUCT_NOT_FOUND, CommonError.PRODUCT_NOT_FOUND.getMessage())
        );
        item.setState(false);
        cartItemsRepository.save(item);
        return CartItemsResponse.builder()
                .quantity(item.getQuantity())
                .userIdx(item.getUserIdx())
                .productId(item.getProductId())
                .message("장바구니에서 상품 삭제가 완료되었습니다.")
                .build();
    }

    public CartItemsResponse updateCartItem(CartItemsRequestDTO cartItemsRequestDTO , Long cartItemId) {
        CartItems updateItem = cartItemsRepository.findById(cartItemId).orElseThrow(
                () -> new MyPageException(CommonError.PRODUCT_NOT_FOUND, CommonError.PRODUCT_NOT_FOUND.getMessage())
        );

        if(updateItem.getProduct().getAmount() < cartItemsRequestDTO.getQuantity()){
            throw new MyPageException(CommonError.CART_ITEMS_NOT_ADDED,CommonError.CART_ITEMS_NOT_ADDED.getMessage()+ String.format("(현재 남은 수량 : %d 개)",updateItem.getProduct().getAmount()) );
        }

        updateItem.setQuantity(cartItemsRequestDTO.getQuantity());
        cartItemsRepository.save(updateItem);
        return CartItemsResponse.builder()
                .userIdx(updateItem.getUserIdx())
                .productId(updateItem.getProductId())
                .quantity(updateItem.getQuantity())
                .message("장바구니에서 상품 수량 수정이 완료되었습니다.")
                .build();
    }

    @Transactional
    public OrderItemsResponse orderCartItem(OrderItemsRequestDTO requestDTO) {

            Orders order = ordersRepository.save(Orders.builder().userIdx(requestDTO.getUserIdx()).state(true).build());

        System.out.println(order);
            int totalPrice = 0;

            for(Long cartItemid :requestDTO.getOrderItems()){
                CartItems cartItems = cartItemsRepository.findById(cartItemid).orElseThrow(
                        () -> new MyPageException(CommonError.PRODUCT_NOT_FOUND, CommonError.PRODUCT_NOT_FOUND.getMessage())
                );
                Product product = cartItems.getProduct();
                int amount = product.getAmount();
                if(amount < cartItems.getQuantity()){
                    //임시 코드
                    throw new MyPageException(CommonError.PRODUCT_NOT_ORDERED,CommonError.PRODUCT_NOT_ORDERED.getMessage()+ String.format("(현재 남은 수량 : %d 개)",amount));
                }else if (amount == cartItems.getQuantity()){
                    product.setAmount(0);
                    product.setState(ProductState.N);
                }else{
                    product.setAmount(amount - cartItems.getQuantity());
                }

                totalPrice += product.getPrice()*cartItems.getQuantity();
                productRepository.save(product); //product 수정
                cartItems.setState(false);
                cartItemsRepository.save(cartItems);//cartitems 수정
                orderItemRepository.save(OrderItem.builder().id(cartItemid).cartItemsId(cartItemid).ordersId(order.getId()).build());
            }

            Users users = usersRepository.findById(requestDTO.getUserIdx()).orElseThrow(
                    () -> new MyPageException(CommonError.USER_NOT_FOUND, CommonError.USER_NOT_FOUND.getMessage())
            );

            if(totalPrice> users.getCashes()){
                throw new MyPageException(CommonError.USER_CASHES_NOT_ENOUGH, CommonError.USER_CASHES_NOT_ENOUGH.getMessage());
            }

            order.setTotalPrice(totalPrice);
            ordersRepository.save(order);
            users.setCashes(users.getCashes()-totalPrice);
            usersRepository.save(users);

        return OrderItemsResponse.builder()
                .orderItems(requestDTO.getOrderItems())
                .message("상품 주문이 완료되었습니다.")
                .userIdx(requestDTO.getUserIdx())
                .build();
    }

    public OrderListResponse getOrder(OrderItemsRequestDTO requestDTO) {

        List<ProductListResponseDTO> orderList = new ArrayList<>();

         int totalPrice = 0;

        for(Long cartItemid :requestDTO.getOrderItems()){

            CartItems cartItem = cartItemsRepository.findById(cartItemid).orElseThrow(
                    () -> new MyPageException(CommonError.PRODUCT_NOT_FOUND, CommonError.PRODUCT_NOT_FOUND.getMessage())
            );




            Product product = cartItem.getProduct();

            if(cartItem.getQuantity()> product.getAmount()){
                throw new MyPageException(CommonError.PRODUCT_NOT_ORDERED,CommonError.PRODUCT_NOT_ORDERED.getMessage()+String.format("(상품 %s 현재 남은 수량 : %d 개)",product.getItemName(),product.getAmount()));
            }
            if(product.getState() == ProductState.N || product.getAmount() < 1 ){
                throw new MyPageException(CommonError.PRODUCT_NOT_FOUND,CommonError.PRODUCT_NOT_FOUND.getMessage());
            }

            orderList.add(
                    ProductListResponseDTO.builder()
                            .username(product.getUser().getUserName())
                            .productId(product.getId())
                            .mainImg(product.getImgs().get(0))
                            .price(product.getPrice())
                            .amount(cartItem.getQuantity())
                            .name(product.getItemName())
                            .cartItemId(cartItemid).build()

            );

            totalPrice += product.getPrice()*cartItem.getQuantity();
        }

        Users user = usersRepository.findById(requestDTO.getUserIdx()).orElseThrow(
                () -> new MyPageException(CommonError.USER_NOT_FOUND, CommonError.USER_NOT_FOUND.getMessage())
        );

        return OrderListResponse.builder()
                .itemList(orderList)
                .userName(user.getUserName())
                .userAddress(user.getAddress())
                .userPhone(user.getPhone())
                .totalPrice(totalPrice)
                .build();
    }
}
