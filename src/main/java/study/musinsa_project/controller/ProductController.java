package study.musinsa_project.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.musinsa_project.dto.product.ProductRegisterRequestDto;
import study.musinsa_project.dto.product.ProductRegisterResponseDto;
import study.musinsa_project.dto.product.ProductSummaryDto;
import study.musinsa_project.service.ProductService;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;


    // 상품등록
    @PostMapping("/register")
    public ResponseEntity<ProductRegisterResponseDto> registerProduct(@RequestBody ProductRegisterRequestDto productRegisterRequestDTO)
    {
        return productService.registerProduct(productRegisterRequestDTO);
    }


    // 유저가 등록한 상품 중 state 가 Y 인 상품만 조회
    @GetMapping("/user/{userId}/active")
    public ResponseEntity<List<ProductSummaryDto>> getUserProducts(@PathVariable Long userId)
    {
        return productService.getUserProducts(userId);
    }

    // 유저가 등록한 상품 중 판매 종료된 상품 조회
    @GetMapping("/user/{userId}/expired")
    public ResponseEntity<List<ProductSummaryDto>> getExpiredUserProducts(@PathVariable Long userId)
    {
        return productService.getExpiredUserProducts(userId);
    }


    // 유저는 자신이 팔고있는 물품의 재고 수정 가능

    @PutMapping("/{productId}/amount")
    public ResponseEntity<String> updateProductAmount(@PathVariable Long productId, @RequestParam Long userId, @RequestParam int amount)
    {
        return ResponseEntity.ok(productService.updateProductAmount(productId, userId, amount));
    }

}
