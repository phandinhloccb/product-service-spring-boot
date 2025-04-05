package com.loc.microservices.product_service_spring_boot.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.loc.microservices.product_service_spring_boot.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
}