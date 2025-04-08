package com.loc.microservices.product_service_spring_boot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.loc.microservices.product_service_spring_boot.dto.ProductRequest;
import com.loc.microservices.product_service_spring_boot.dto.ProductResponse;
import com.loc.microservices.product_service_spring_boot.model.Product;
import com.loc.microservices.product_service_spring_boot.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
        .name(productRequest.name())
        .description(productRequest.description())
        .price(productRequest.price())
        .skuCode(productRequest.skuCode())
        .build();

        productRepository.save(product);
        log.info("Product created successfully");

        return mapToProductResponse(product);
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
        .stream()
        .map(this::mapToProductResponse)
        .toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getSkuCode(), product.getPrice());
    }
    
    
    
}

