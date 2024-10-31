package study.musinsa_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.musinsa_project.dto.CartItemsRequestDTO;
import study.musinsa_project.dto.ProductListResponseDTO;
import study.musinsa_project.service.CartService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<ProductListResponseDTO>> getCartItemsByUserId(@PathVariable("userId") Integer userId) {

        List<ProductListResponseDTO> responseDTOS = cartService.getCartItemsByUserId(userId);

        return ResponseEntity.ok().body(responseDTOS);
    }

    @PostMapping("/item")
    public ResponseEntity<CartItemsRequestDTO> createCartItem(@RequestBody CartItemsRequestDTO cartItemsRequestDTO) {
        CartItemsRequestDTO requestDTO = cartService.createCartItem(cartItemsRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(requestDTO);
    }

    @PutMapping("/item/{cartItemId}")
    public ResponseEntity<CartItemsRequestDTO> updateCartItem(@RequestBody CartItemsRequestDTO cartItemsRequestDTO, @PathVariable("cartItemId") Long cartItemId) {
        CartItemsRequestDTO requestDTO = cartService.updateCartItem(cartItemsRequestDTO, cartItemId);
        return ResponseEntity.status(HttpStatus.OK).body(requestDTO);
    }

    @DeleteMapping("/item/{cartItemId}")
    public ResponseEntity<Object> deleteCartItem(@PathVariable("cartItemId") Long cartItemId) {
        cartService.deleteCartItem(cartItemId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }






}
