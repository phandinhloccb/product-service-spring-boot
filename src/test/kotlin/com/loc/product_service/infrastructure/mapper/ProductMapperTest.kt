package com.loc.product_service.infrastructure.mapper

import com.loc.product_service.domain.model.Product
import com.loc.product_service.infrastructure.repository.entity.ProductDocumentEntity
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class ProductDocumentEntityMapperTest {

    @Test
    fun `toProduct should map ProductDocumentEntity to Product`() {
        // Given
        val document = ProductDocumentEntity(
            id = "test-id",
            name = "iPhone 15",
            description = "Latest iPhone",
            skuCode = "IPHONE-15",
            price = BigDecimal("999.99")
        )

        // When
        val result = document.toProduct()

        // Then
        assertEquals("test-id", result.id)
        assertEquals("iPhone 15", result.name)
        assertEquals("Latest iPhone", result.description)
        assertEquals("IPHONE-15", result.skuCode)
        assertEquals(BigDecimal("999.99"), result.price)
    }

    @Test
    fun `toProduct should handle null id`() {
        // Given
        val document = ProductDocumentEntity(
            id = null,
            name = "Test Product",
            description = "Test Description",
            skuCode = "TEST-001",
            price = BigDecimal("100.00")
        )

        // When
        val result = document.toProduct()

        // Then
        assertNull(result.id)
        assertEquals("Test Product", result.name)
        assertEquals("Test Description", result.description)
        assertEquals("TEST-001", result.skuCode)
        assertEquals(BigDecimal("100.00"), result.price)
    }

    @Test
    fun `toProductDocumentEntity should map Product to ProductDocumentEntity`() {
        // Given
        val product = Product(
            id = "test-id",
            name = "MacBook Pro",
            description = "Professional laptop",
            skuCode = "MACBOOK-PRO-16",
            price = BigDecimal("2499.00")
        )

        // When
        val result = product.toProductDocumentEntity()

        // Then
        assertEquals("test-id", result.id)
        assertEquals("MacBook Pro", result.name)
        assertEquals("Professional laptop", result.description)
        assertEquals("MACBOOK-PRO-16", result.skuCode)
        assertEquals(BigDecimal("2499.00"), result.price)
    }

    @Test
    fun `toProductDocumentEntity should handle null id`() {
        // Given
        val product = Product(
            id = null,
            name = "New Product",
            description = "New Description",
            skuCode = "NEW-001",
            price = BigDecimal("250.00")
        )

        // When
        val result = product.toProductDocumentEntity()

        // Then
        assertNull(result.id)
        assertEquals("New Product", result.name)
        assertEquals("New Description", result.description)
        assertEquals("NEW-001", result.skuCode)
        assertEquals(BigDecimal("250.00"), result.price)
    }

    @Test
    fun `should maintain data integrity during round trip conversion`() {
        // Given
        val originalProduct = Product(
            id = "round-trip-id",
            name = "Round Trip Product",
            description = "Testing round trip",
            skuCode = "ROUND-TRIP-001",
            price = BigDecimal("123.45")
        )

        // When
        val document = originalProduct.toProductDocumentEntity()
        val convertedProduct = document.toProduct()

        // Then
        assertEquals(originalProduct.id, convertedProduct.id)
        assertEquals(originalProduct.name, convertedProduct.name)
        assertEquals(originalProduct.description, convertedProduct.description)
        assertEquals(originalProduct.skuCode, convertedProduct.skuCode)
        assertEquals(originalProduct.price, convertedProduct.price)
    }

    @Test
    fun `toProducts should convert list of ProductDocumentEntity to list of Product`() {
        // Given
        val documents = listOf(
            ProductDocumentEntity(
                id = "1",
                name = "Product 1",
                description = "Description 1",
                skuCode = "PROD-001",
                price = BigDecimal("100.00")
            ),
            ProductDocumentEntity(
                id = "2",
                name = "Product 2",
                description = "Description 2",
                skuCode = "PROD-002",
                price = BigDecimal("200.00")
            )
        )

        // When
        val result = documents.toProducts()

        // Then
        assertEquals(2, result.size)
        assertEquals("Product 1", result[0].name)
        assertEquals("PROD-001", result[0].skuCode)
        assertEquals("Product 2", result[1].name)
        assertEquals("PROD-002", result[1].skuCode)
    }
} 