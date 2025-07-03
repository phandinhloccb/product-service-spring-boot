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

class AddProductServiceTest {

    private lateinit var productRepositoryPort: ProductRepositoryPort
    private lateinit var addProductService: AddProductService

    @BeforeEach
    fun setUp() {
        productRepositoryPort = mockk()
        addProductService = AddProductService(productRepositoryPort)
    }

    @Test
    fun `addProduct should save and return product`() {
        // Given
        val product = Product(
            name = "iPhone 15",
            description = "Latest iPhone",
            skuCode = "IPHONE-15",
            price = BigDecimal("999.99")
        )
        val savedProduct = product.copy(id = "generated-id")
        every { productRepositoryPort.addProduct(product) } returns savedProduct

        // When
        val result = addProductService.addProduct(product)

        // Then
        assertNotNull(result.id)
        assertEquals("iPhone 15", result.name)
        assertEquals("Latest iPhone", result.description)
        assertEquals("IPHONE-15", result.skuCode)
        assertEquals(BigDecimal("999.99"), result.price)
        verify(exactly = 1) { productRepositoryPort.addProduct(product) }
    }

    @Test
    fun `addProduct should handle product with all fields`() {
        // Given
        val product = Product(
            name = "MacBook Pro",
            description = "Professional laptop",
            skuCode = "MACBOOK-PRO-16",
            price = BigDecimal("2499.00")
        )
        val savedProduct = product.copy(id = "macbook-id")
        every { productRepositoryPort.addProduct(product) } returns savedProduct

        // When
        val result = addProductService.addProduct(product)

        // Then
        assertEquals("macbook-id", result.id)
        assertEquals("MacBook Pro", result.name)
        assertEquals("Professional laptop", result.description)
        assertEquals("MACBOOK-PRO-16", result.skuCode)
        assertEquals(BigDecimal("2499.00"), result.price)
        verify(exactly = 1) { productRepositoryPort.addProduct(product) }
    }

    @Test
    fun `addProduct should preserve price precision`() {
        // Given
        val product = Product(
            name = "Test Product",
            description = "Test Description",
            skuCode = "TEST-001",
            price = BigDecimal("123.456")
        )
        val savedProduct = product.copy(id = "test-id")
        every { productRepositoryPort.addProduct(product) } returns savedProduct

        // When
        val result = addProductService.addProduct(product)

        // Then
        assertEquals(BigDecimal("123.456"), result.price)
        verify(exactly = 1) { productRepositoryPort.addProduct(product) }
    }
} 