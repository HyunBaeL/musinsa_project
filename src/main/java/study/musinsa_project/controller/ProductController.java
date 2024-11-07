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


    @PostMapping("/register")
    public ResponseEntity<ProductRegisterResponseDto> registerProduct(@ModelAttribute ProductRegisterRequestDto productRegisterRequestDTO)
    {
        // 이미지 목록을 DTO에 세팅 (기본적으로 @ModelAttribute가 이미 필드에 매핑)
        return ResponseEntity.ok(productService.registerProduct(productRegisterRequestDTO));
    }



    // 상품삭제 : state 필드만 'Y' 에서 'N' 으로 바꿔야하기 때문에 PUT 사용  // 본인 상품만 삭제가능
    @PutMapping("/{productId}/delete")
    public ResponseEntity<String> updateProduct(@PathVariable Long productId, @RequestParam Long userId)
    {
        return ResponseEntity.ok(productService.deleteItem(productId, userId));
    }

    // 유저가 등록한 상품 중 state 가 Y 인 상품만 조회
    @GetMapping("/user/{userId}/active")
    public ResponseEntity<List<ProductSummaryDto>> getUserProducts(@PathVariable Long userId)
    {
        return ResponseEntity.ok(productService.getUserProducts(userId));
    }

    // 유저가 등록한 상품 중 판매 종료된 상품 조회
    @GetMapping("/user/{userId}/expired")
    public ResponseEntity<List<ProductSummaryDto>> getExpiredUserProducts(@PathVariable Long userId)
    {
        return ResponseEntity.ok(productService.getExpiredUserProducts(userId));
    }


    // 유저는 자신이 팔고있는 물품의 재고 수정 가능

    @PutMapping("/{productId}/amount")
    public ResponseEntity<String> updateProductAmount(@PathVariable Long productId, @RequestParam Long userId, @RequestParam int amount)
    {
        return ResponseEntity.ok(productService.updateProductAmount(productId, userId, amount));
    }

}
