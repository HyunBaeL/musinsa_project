package study.musinsa_project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.musinsa_project.dto.*;
import study.musinsa_project.entity.CartItems;
import study.musinsa_project.entity.OrderItem;
import study.musinsa_project.entity.Orders;
import study.musinsa_project.entity.Product;
import study.musinsa_project.repository.CartItemsRepository;
import study.musinsa_project.repository.OrderItemRepository;
import study.musinsa_project.repository.OrdersRepository;
import study.musinsa_project.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemsRepository cartItemsRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrdersRepository ordersRepository;
    private final ProductRepository productRepository;

    public List<ProductListResponseDTO> getCartItemsByUserId(Integer userId) {

        return cartItemsRepository.selectUserId(userId)
                .stream().map(item -> ProductListResponseDTO.builder()
                        .id(item.getId())
                        .name(item.getProduct().getItemName())
                        .price(item.getProduct().getPrice())
                        .username(item.getUser().getUserName())
                        .mainImg(item.getProduct().getImgs().get(0))
                        .amount(item.getQuantity())
                        .build()).collect(Collectors.toList());

    }

    public CartItemsRequestDTO createCartItem(CartItemsRequestDTO cartItemsRequestDTO) {

        cartItemsRepository.save(new CartItems(cartItemsRequestDTO.getQuantity(),cartItemsRequestDTO.getUserIdx(),cartItemsRequestDTO.getProductId()));

        return cartItemsRequestDTO;
    }

    public void deleteCartItem(Long cartItemId) {
        cartItemsRepository.deleteById(cartItemId);
    }

    public CartItemsRequestDTO updateCartItem(CartItemsRequestDTO cartItemsRequestDTO , Long cartItemId) {
        CartItems updateItem = cartItemsRepository.findById(cartItemId).orElse(null);
        updateItem.setQuantity(cartItemsRequestDTO.getQuantity());
        cartItemsRepository.save(updateItem);
        return cartItemsRequestDTO;
    }

    @Transactional
    public MessageResponseDTO orderCartItem(OrderItemsRequestDTO requestDTO) {
        try {
            Orders order = ordersRepository.save(Orders.builder().userIdx(requestDTO.getUserIdx()).state(true).build());

            for(Long cartItemid :requestDTO.getOrderItems()){
                CartItems cartItems = cartItemsRepository.findById(cartItemid).orElseThrow();
                Product product = cartItems.getProduct();
                if(cartItems.getProduct().getAmount() - cartItems.getQuantity() <0){
                    //임시 코드
                    throw new Exception();
                }else if (cartItems.getProduct().getAmount() == cartItems.getQuantity()){
                    product.setAmount(0);
                    product.setState(Product.State.N);
                }else{
                    product.setAmount(product.getAmount() - cartItems.getQuantity());
                }
                productRepository.save(product); //product 수정
                cartItems.setState(false);
                cartItemsRepository.save(cartItems);//cartitems 수정
                orderItemRepository.save(OrderItem.builder().id(cartItemid).cartItemsId(cartItemid).ordersId(order.getId()).build());
            }


        } catch (Exception e) {
            e.printStackTrace();
            return new MessageResponseDTO("주문에 실패했습니다.");
        }


        return new MessageResponseDTO("주문이 완료되었습니다.");
    }
}
