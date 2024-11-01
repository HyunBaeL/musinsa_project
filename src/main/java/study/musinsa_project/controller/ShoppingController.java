package study.musinsa_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.musinsa_project.dto.ProductDetailResposeDTO;
import study.musinsa_project.dto.ProductListResponseDTO;
import study.musinsa_project.service.ProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ShoppingController {
    private final ProductService productService;

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductDetailResposeDTO> getProductById(@PathVariable("productId") Long productId) {
        ProductDetailResposeDTO responseDTO = productService.getProductById(productId);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/main")
    public ResponseEntity<List<ProductListResponseDTO>> getMainProduct() {
        List<ProductListResponseDTO> responseDTOS = productService.getProductAll();
        return ResponseEntity.ok(responseDTOS);
    }

}
