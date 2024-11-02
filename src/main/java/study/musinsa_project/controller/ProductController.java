package study.musinsa_project.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.musinsa_project.dto.product.ProductRegisterDto;
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
    public ResponseEntity<String> registerProduct(@RequestBody ProductRegisterDto productRegisterDTO)
    {
        Long id =  productService.registerProduct(productRegisterDTO);
        return ResponseEntity.ok( "[" + id + "] " + "상품 등록 성공");
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


    // 상품삭제 : state 필드만 'Y' 에서 'N' 으로 바꿔야하기 때문에 PUT 사용  // 본인 상품만 삭제가능
    @PutMapping("/{productId}/delete")
    public ResponseEntity<String> updateProduct(@PathVariable Long productId, @RequestParam Long userId)
    {
        return ResponseEntity.ok(productService.deleteItem(productId, userId));
    }



    // 유저는 자신이 팔고있는 물품의 재고 수정 가능

    @PutMapping("/{productId}/amount")
    public ResponseEntity<String> updateProductAmount(@PathVariable Long productId, @RequestParam Long userId, @RequestParam int amount)
    {
        return ResponseEntity.ok(productService.updateProductAmount(productId, userId, amount));
    }








//
//
//    // 쇼핑몰 물품 상세 페이지 // state 가 'N'인 경우 제외
//    @GetMapping("/products/{productId}")
//    public ResponseEntity<ProductDetailDto> productDetailInfo(@PathVariable Long productId)
//    {
//        return ResponseEntity.ok(productService.detailProduct(productId));
//    }



//
//    // 유저들이 등록한 모든 상품들중 이미지, 상품명, 가격을 메인 페이지에 뿌려주기 // state 가 'Y'인 상품들만
//    @GetMapping("/products")
//    public ResponseEntity<List<ProductSummaryDto>> productList()
//    {
//        return productService.showAllProduct();
//    }


}
