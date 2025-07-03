package com.loc.product_service.controller.mapper

import com.loc.product_service.domain.model.Product
import com.loc.productservice.model.AddProductRequest
import com.loc.productservice.model.ProductResponse

fun AddProductRequest.toProduct(): Product {
    return Product(
        name = this.name,
        description = this.description,
        skuCode = this.skuCode,
        price = this.price
    )
}

fun Product.toResponse(): ProductResponse {
    return ProductResponse(
        name = this.name,
        description = this.description,
        skuCode = this.skuCode,
        price = this.price
    )
}