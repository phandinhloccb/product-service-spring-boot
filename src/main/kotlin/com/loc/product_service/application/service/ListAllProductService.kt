package com.loc.product_service.application.service

import com.loc.product_service.application.port.ProductRepositoryPort
import com.loc.product_service.domain.model.Product

class ListAllProductService(private val productRepository: ProductRepositoryPort) {
    fun listAllProducts(): List<Product> {
        return productRepository.findAll()
    }
}