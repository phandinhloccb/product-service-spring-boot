package com.loc.microservices.product_service_spring_boot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.loc.microservices.product_service_spring_boot.dto.ProductRequest;
import com.loc.microservices.product_service_spring_boot.dto.ProductResponse;
import com.loc.microservices.product_service_spring_boot.service.ProductService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private ProductService productService;
    
    @Autowired
    private ApplicationContext context;
    
    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
        System.out.println("ProductService injected: " + (productService != null));
    }
    
    @GetMapping("/debug")
    public String debug() {
        return "ProductService bean exists: " + context.containsBean("productService");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@RequestBody ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }
}
