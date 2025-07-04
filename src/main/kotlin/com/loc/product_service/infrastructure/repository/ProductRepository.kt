package com.loc.product_service.infrastructure.repository

import com.loc.product_service.domain.model.Product
import com.loc.product_service.infrastructure.repository.entity.ProductDocumentEntity
import org.springframework.data.mongodb.repository.MongoRepository

interface ProductRepository: MongoRepository<ProductDocumentEntity, Long> {
}