package com.loc.microservices.product_service_spring_boot;

import org.springframework.boot.SpringApplication;

public class TestProductServiceSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.from(ProductServiceSpringBootApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
