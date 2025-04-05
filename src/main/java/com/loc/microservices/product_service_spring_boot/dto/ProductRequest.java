package com.loc.microservices.product_service_spring_boot.dto;

import java.math.BigDecimal;

public record ProductRequest(String name, String description, BigDecimal price)  {

}
