package com.loc.microservices.product_service_spring_boot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import java.math.BigDecimal;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("products")
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
}
