package com.loc.product_service.controller

import com.loc.product_service.application.service.AddProductService
import com.loc.product_service.controller.mapper.toProduct
import com.loc.product_service.controller.mapper.toResponse
import com.loc.productservice.model.AddProductRequest
import com.loc.productservice.model.ProductRequest
import io.micrometer.core.ipc.http.HttpSender
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/product")
class ProductController(
    private val addProductService: AddProductService
) {
    @PostMapping("/add")
    fun addProduct(@RequestBody addProductRequest: AddProductRequest): ResponseEntity<Any> {
        val result = addProductService.addProduct(addProductRequest.toProduct())

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(result.toResponse())
    }
}