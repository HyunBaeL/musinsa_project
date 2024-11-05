package study.musinsa_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.musinsa_project.dto.*;
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
    public ResponseEntity<CartItemsResponse> createCartItem(@RequestBody CartItemsRequestDTO cartItemsRequestDTO) {
        CartItemsResponse requestDTO = cartService.createCartItem(cartItemsRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(requestDTO);
    }

    @PutMapping("/item/{cartItemId}")
    public ResponseEntity<CartItemsResponse> updateCartItem(@RequestBody CartItemsRequestDTO cartItemsRequestDTO, @PathVariable("cartItemId") Long cartItemId) {
        CartItemsResponse response = cartService.updateCartItem(cartItemsRequestDTO, cartItemId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/item/{cartItemId}")
    public ResponseEntity<CartItemsResponse> deleteCartItem(@PathVariable("cartItemId") Long cartItemId) {
        CartItemsResponse response = cartService.deleteCartItem(cartItemId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/order")
    public ResponseEntity<OrderListResponse> getOrder(@RequestBody OrderItemsRequestDTO requestDTO) {

        OrderListResponse response = cartService.getOrder(requestDTO);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/order")
    public ResponseEntity<OrderItemsResponse> orderCartItem(@RequestBody OrderItemsRequestDTO requestDTO) {

        OrderItemsResponse response = cartService.orderCartItem(requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }






}
