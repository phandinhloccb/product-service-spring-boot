package com.loc.microservices.product_service_spring_boot.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.loc.microservices.product_service_spring_boot.dto.ProductRequest;
import com.loc.microservices.product_service_spring_boot.model.Product;
import com.loc.microservices.product_service_spring_boot.service.ProductService;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ProductGraphQLController {

    private final ProductService productService;

    @QueryMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
    
    @QueryMapping
    public Optional<Product> getProductById(@Argument String id) {
        return productService.getProductById(id);
    }

    @MutationMapping
    public Product createProduct(@Argument("productInput") ProductRequest productInput) {
        return productService.createProduct(productInput);
    }
} 