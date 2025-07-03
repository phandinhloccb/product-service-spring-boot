package com.loc.product_service.application.service

import com.loc.product_service.application.port.ProductRepositoryPort
import com.loc.product_service.domain.model.Product
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class ListAllProductServiceTest {

    private lateinit var productRepositoryPort: ProductRepositoryPort
    private lateinit var listAllProductService: ListAllProductService

    @BeforeEach
    fun setUp() {
        productRepositoryPort = mockk()
        listAllProductService = ListAllProductService(productRepositoryPort)
    }

    @Test
    fun `listAllProducts should return all products`() {
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
            ),
            Product(
                id = "3",
                name = "MacBook Pro",
                description = "Professional laptop",
                skuCode = "MACBOOK-PRO",
                price = BigDecimal("2499.00")
            )
        )
        every { productRepositoryPort.findAll() } returns products

        // When
        val result = listAllProductService.listAllProducts()

        // Then
        assertEquals(3, result.size)
        assertEquals("iPhone 15", result[0].name)
        assertEquals("Samsung Galaxy S24", result[1].name)
        assertEquals("MacBook Pro", result[2].name)
        verify(exactly = 1) { productRepositoryPort.findAll() }
    }

    @Test
    fun `listAllProducts should return empty list when no products exist`() {
        // Given
        every { productRepositoryPort.findAll() } returns emptyList()

        // When
        val result = listAllProductService.listAllProducts()

        // Then
        assertTrue(result.isEmpty())
        verify(exactly = 1) { productRepositoryPort.findAll() }
    }

    @Test
    fun `listAllProducts should preserve product data integrity`() {
        // Given
        val products = listOf(
            Product(
                id = "test-id",
                name = "Test Product",
                description = "Test Description",
                skuCode = "TEST-001",
                price = BigDecimal("123.45")
            )
        )
        every { productRepositoryPort.findAll() } returns products

        // When
        val result = listAllProductService.listAllProducts()

        // Then
        assertEquals(1, result.size)
        val product = result[0]
        assertEquals("test-id", product.id)
        assertEquals("Test Product", product.name)
        assertEquals("Test Description", product.description)
        assertEquals("TEST-001", product.skuCode)
        assertEquals(BigDecimal("123.45"), product.price)
        verify(exactly = 1) { productRepositoryPort.findAll() }
    }
} 