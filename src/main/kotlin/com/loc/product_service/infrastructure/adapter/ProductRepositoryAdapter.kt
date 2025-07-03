package com.loc.product_service.infrastructure.adapter

import com.loc.product_service.application.port.ProductRepositoryPort
import com.loc.product_service.domain.model.Product
import com.loc.product_service.infrastructure.mapper.toProduct
import com.loc.product_service.infrastructure.mapper.toProductDocumentEntity
import com.loc.product_service.infrastructure.repository.ProductRepository

class ProductRepositoryAdapter(private val productRepository: ProductRepository): ProductRepositoryPort {
    override fun addProduct(product: Product): Product {
        val productDocument = product.toProductDocumentEntity()
        val savedProduct = productRepository.save(productDocument)
        return savedProduct.toProduct()
    }
}