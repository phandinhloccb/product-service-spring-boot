package com.loc.product_service.controller

import com.loc.product_service.application.service.AddProductService
import com.loc.product_service.application.service.ListAllProductService
import com.loc.product_service.controller.mapper.toProduct
import com.loc.product_service.controller.mapper.toResponse
import com.loc.productservice.model.AddProductRequest
import com.loc.productservice.model.ProductResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/product")
class ProductController(
    private val addProductService: AddProductService,
    private val listAllProductService: ListAllProductService
) {
    @PostMapping("/add")
    fun addProduct(@RequestBody addProductRequest: AddProductRequest): ResponseEntity<ProductResponse> {
        val result = addProductService.addProduct(addProductRequest.toProduct())

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(result.toResponse())
    }

    @GetMapping("/all")
    fun listAllProducts(): ResponseEntity<List<ProductResponse>> {
        val result = listAllProductService.listAllProducts()

        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(result.map { it.toResponse() })
    }
}