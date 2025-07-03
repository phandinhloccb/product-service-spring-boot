package com.loc.product_service.application.service

import com.loc.product_service.application.port.ProductRepositoryPort
import com.loc.product_service.domain.model.Product
import org.springframework.stereotype.Service

@Service
class AddProductService(
    private val productRepositoryPort: ProductRepositoryPort
) {
    fun addProduct(product: Product): Product {
        return productRepositoryPort.addProduct(product)
    }
}