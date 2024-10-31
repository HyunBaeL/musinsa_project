package study.musinsa_project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import study.musinsa_project.dto.ProductDetailResposeDTO;
import study.musinsa_project.dto.ProductListResponseDTO;
import study.musinsa_project.entity.Product;
import study.musinsa_project.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductDetailResposeDTO getProductById(Long productId) {
        return productRepository.findById(productId)
                .map(item -> item.getProductDetailResposeDTO(item))
                .orElseThrow(() -> new RuntimeException("해당 상품 존재하지 않습니다."));

    }

    public List<ProductListResponseDTO> getProductAll() {

        return productRepository.findAllByOrderByIdDesc()
                .stream().filter(product -> product.getAmount() > 0)
                        .map(product -> ProductListResponseDTO.builder()
                        .id(product.getId())
                        .price(product.getPrice())
                        .name(product.getItemName())
                        .mainImg(product.getImgs().get(0))
                        .username(product.getUser().getUserName())
                        .amount(product.getAmount())
                        .build())
                .collect(Collectors.toList());
    }
}
