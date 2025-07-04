package com.loc.product_service.infrastructure.adapter

import com.loc.product_service.domain.model.Product
import com.loc.product_service.infrastructure.repository.ProductRepository
import com.loc.product_service.infrastructure.repository.entity.ProductDocumentEntity
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class ProductRepositoryAdapterTest {

    private lateinit var productRepository: ProductRepository
    private lateinit var productRepositoryAdapter: ProductRepositoryAdapter

    @BeforeEach
    fun setUp() {
        productRepository = mockk()
        productRepositoryAdapter = ProductRepositoryAdapter(productRepository)
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
        val savedDocument = ProductDocumentEntity(
            id = "generated-id",
            name = "iPhone 15",
            description = "Latest iPhone",
            skuCode = "IPHONE-15",
            price = BigDecimal("999.99")
        )
        every { productRepository.save(any<ProductDocumentEntity>()) } returns savedDocument

        // When
        val result = productRepositoryAdapter.addProduct(product)

        // Then
        assertEquals("generated-id", result.id)
        assertEquals("iPhone 15", result.name)
        assertEquals("Latest iPhone", result.description)
        assertEquals("IPHONE-15", result.skuCode)
        assertEquals(BigDecimal("999.99"), result.price)
        verify(exactly = 1) { productRepository.save(any<ProductDocumentEntity>()) }
    }

    @Test
    fun `addProduct should handle product without id`() {
        // Given
        val product = Product(
            id = null,
            name = "New Product",
            description = "New Description",
            skuCode = "NEW-001",
            price = BigDecimal("199.99")
        )
        val savedDocument = ProductDocumentEntity(
            id = "auto-generated-id",
            name = "New Product",
            description = "New Description",
            skuCode = "NEW-001",
            price = BigDecimal("199.99")
        )
        every { productRepository.save(any<ProductDocumentEntity>()) } returns savedDocument

        // When
        val result = productRepositoryAdapter.addProduct(product)

        // Then
        assertEquals("auto-generated-id", result.id)
        assertEquals("New Product", result.name)
        assertEquals("NEW-001", result.skuCode)
        verify(exactly = 1) { productRepository.save(any<ProductDocumentEntity>()) }
    }

    @Test
    fun `findAll should return all products`() {
        // Given
        val documents = listOf(
            ProductDocumentEntity(
                id = "1",
                name = "iPhone 15",
                description = "Latest iPhone",
                skuCode = "IPHONE-15",
                price = BigDecimal("999.99")
            ),
            ProductDocumentEntity(
                id = "2",
                name = "Samsung Galaxy S24",
                description = "Android flagship",
                skuCode = "GALAXY-S24",
                price = BigDecimal("899.99")
            ),
            ProductDocumentEntity(
                id = "3",
                name = "MacBook Pro",
                description = "Professional laptop",
                skuCode = "MACBOOK-PRO",
                price = BigDecimal("2499.00")
            )
        )
        every { productRepository.findAll() } returns documents

        // When
        val result = productRepositoryAdapter.findAll()

        // Then
        assertEquals(3, result.size)
        assertEquals("iPhone 15", result[0].name)
        assertEquals("IPHONE-15", result[0].skuCode)
        assertEquals("Samsung Galaxy S24", result[1].name)
        assertEquals("GALAXY-S24", result[1].skuCode)
        assertEquals("MacBook Pro", result[2].name)
        assertEquals("MACBOOK-PRO", result[2].skuCode)
        verify(exactly = 1) { productRepository.findAll() }
    }

    @Test
    fun `findAll should return empty list when no products exist`() {
        // Given
        every { productRepository.findAll() } returns emptyList()

        // When
        val result = productRepositoryAdapter.findAll()

        // Then
        assertTrue(result.isEmpty())
        verify(exactly = 1) { productRepository.findAll() }
    }

    @Test
    fun `findAll should preserve data integrity`() {
        // Given
        val documents = listOf(
            ProductDocumentEntity(
                id = "test-id",
                name = "Test Product",
                description = "Test Description",
                skuCode = "TEST-001",
                price = BigDecimal("123.45")
            )
        )
        every { productRepository.findAll() } returns documents

        // When
        val result = productRepositoryAdapter.findAll()

        // Then
        assertEquals(1, result.size)
        val product = result[0]
        assertEquals("test-id", product.id)
        assertEquals("Test Product", product.name)
        assertEquals("Test Description", product.description)
        assertEquals("TEST-001", product.skuCode)
        assertEquals(BigDecimal("123.45"), product.price)
        verify(exactly = 1) { productRepository.findAll() }
    }

    @Test
    fun `addProduct should preserve price precision`() {
        // Given
        val product = Product(
            name = "Precision Test",
            description = "Testing price precision",
            skuCode = "PRECISION-001",
            price = BigDecimal("999.999")
        )
        val savedDocument = ProductDocumentEntity(
            id = "precision-id",
            name = "Precision Test",
            description = "Testing price precision",
            skuCode = "PRECISION-001",
            price = BigDecimal("999.999")
        )
        every { productRepository.save(any<ProductDocumentEntity>()) } returns savedDocument

        // When
        val result = productRepositoryAdapter.addProduct(product)

        // Then
        assertEquals(BigDecimal("999.999"), result.price)
        verify(exactly = 1) { productRepository.save(any<ProductDocumentEntity>()) }
    }
} 