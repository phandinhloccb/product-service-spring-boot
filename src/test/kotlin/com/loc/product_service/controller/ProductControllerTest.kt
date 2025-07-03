package com.loc.product_service.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.loc.product_service.application.service.AddProductService
import com.loc.product_service.application.service.ListAllProductService
import com.loc.product_service.domain.model.Product
import com.loc.productservice.model.AddProductRequest
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.math.BigDecimal

@WebMvcTest(ProductController::class)
class ProductControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var addProductService: AddProductService

    @MockkBean
    private lateinit var listAllProductService: ListAllProductService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `GET api product all should return all products`() {
        // Given
        val products = listOf(
            Product(
                id = "1",
                name = "iPhone 15",
                description = "Latest iPhone",
                skuCode = "IPHONE-15",
                price = BigDecimal("999.99")
            ),
            Product(
                id = "2",
                name = "Samsung Galaxy S24",
                description = "Android flagship",
                skuCode = "GALAXY-S24",
                price = BigDecimal("899.99")
            )
        )
        every { listAllProductService.listAllProducts() } returns products

        // When & Then
        mockMvc.perform(get("/api/product/all"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").isArray)
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].name").value("iPhone 15"))
            .andExpect(jsonPath("$[0].skuCode").value("IPHONE-15"))
            .andExpect(jsonPath("$[1].name").value("Samsung Galaxy S24"))
            .andExpect(jsonPath("$[1].skuCode").value("GALAXY-S24"))
    }

    @Test
    fun `GET api product all should return empty list when no products`() {
        // Given
        every { listAllProductService.listAllProducts() } returns emptyList()

        // When & Then
        mockMvc.perform(get("/api/product/all"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").isArray)
            .andExpect(jsonPath("$.length()").value(0))
    }

    @Test
    fun `POST api product add should create product`() {
        // Given
        val addProductRequest = AddProductRequest(
            name = "MacBook Pro",
            description = "Professional laptop",
            skuCode = "MACBOOK-PRO-16",
            price = BigDecimal("2499.00")
        )
        val createdProduct = Product(
            id = "generated-id",
            name = "MacBook Pro",
            description = "Professional laptop",
            skuCode = "MACBOOK-PRO-16",
            price = BigDecimal("2499.00")
        )
        every { addProductService.addProduct(any()) } returns createdProduct

        // When & Then
        mockMvc.perform(
            post("/api/product/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addProductRequest))
        )
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name").value("MacBook Pro"))
            .andExpect(jsonPath("$.description").value("Professional laptop"))
            .andExpect(jsonPath("$.skuCode").value("MACBOOK-PRO-16"))
            .andExpect(jsonPath("$.price").value(2499.00))
    }

    @Test
    fun `POST api product add should handle different price formats`() {
        // Given
        val addProductRequest = AddProductRequest(
            name = "Test Product",
            description = "Test Description",
            skuCode = "TEST-001",
            price = BigDecimal("123.45")
        )
        val createdProduct = Product(
            id = "test-id",
            name = "Test Product",
            description = "Test Description",
            skuCode = "TEST-001",
            price = BigDecimal("123.45")
        )
        every { addProductService.addProduct(any()) } returns createdProduct

        // When & Then
        mockMvc.perform(
            post("/api/product/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addProductRequest))
        )
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name").value("Test Product"))
            .andExpect(jsonPath("$.skuCode").value("TEST-001"))
            .andExpect(jsonPath("$.price").value(123.45))
    }
}