package study.musinsa_project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.musinsa_project.dto.CartItemsRequestDTO;
import study.musinsa_project.dto.ProductListResponseDTO;
import study.musinsa_project.entity.CartItems;
import study.musinsa_project.repository.CartItemsRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemsRepository cartItemsRepository;

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
}
