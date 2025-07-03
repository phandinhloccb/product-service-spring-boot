package com.loc.product_service.infrastructure.mapper

import com.loc.product_service.domain.model.Product
import com.loc.product_service.infrastructure.repository.entity.ProductDocumentEntity

fun Product.toProductDocumentEntity(): ProductDocumentEntity {
    return ProductDocumentEntity(
        id = id,
        name = name,
        description = description,
        skuCode = skuCode,
        price = price
    )
}

fun ProductDocumentEntity.toProduct(): Product {
    return Product(
        id = id,
        name = name,
        description = description,
        skuCode = skuCode,
        price = price
    )
}

