package com.loc.product_service.domain.model

import java.math.BigDecimal

data class Product (
    val id: String? = null,
    val name: String,
    val description: String,
    val skuCode: String,
    val price: BigDecimal
){}