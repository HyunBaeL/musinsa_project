package study.musinsa_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<Page<ProductListResponseDTO>> getMainProduct(
            @RequestParam(value = "page", defaultValue = "0") int page) {

        Pageable pageable = PageRequest.of(page, 10);
        Page<ProductListResponseDTO> responseDTOS = productService.getProductAll(null, pageable);
        return ResponseEntity.ok(responseDTOS);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductListResponseDTO>> searchProductByKeyword(@RequestParam String keyword,
                                                                               @RequestParam(value = "page", defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10);

        Page<ProductListResponseDTO> responseDTOS = productService.getProductAll(keyword, pageable);
        return ResponseEntity.ok(responseDTOS);
    }

}
