package com.loc.product_service.application.port

import com.loc.product_service.domain.model.Product

interface ProductRepositoryPort {
    fun addProduct(product: Product): Product
    fun findAll(): List<Product>
}