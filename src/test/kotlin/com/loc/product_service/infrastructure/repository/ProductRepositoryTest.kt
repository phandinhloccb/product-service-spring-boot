package com.loc.product_service.infrastructure.repository

import com.loc.product_service.infrastructure.repository.entity.ProductDocumentEntity
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.math.BigDecimal

@DataMongoTest
@Testcontainers
class ProductRepositoryTest {

    companion object {
        @Container
        @JvmStatic
        val mongoDBContainer = MongoDBContainer("mongo:7.0.5")

        @DynamicPropertySource
        @JvmStatic
        fun configureProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.data.mongodb.uri") { mongoDBContainer.replicaSetUrl }
        }
    }

    @Autowired
    private lateinit var productRepository: ProductRepository

    @BeforeEach
    fun setUp() {
        productRepository.deleteAll()
    }

    @Test
    fun `should save and retrieve product`() {
        // Given
        val product = ProductDocumentEntity(
            name = "Test Product",
            description = "Test Description",
            skuCode = "TEST-001",
            price = BigDecimal("99.99")
        )

        // When
        val savedProduct = productRepository.save(product)
        val foundProduct = productRepository.findById(savedProduct.id!!)

        // Then
        assertTrue(foundProduct.isPresent)
        assertEquals("Test Product", foundProduct.get().name)
        assertEquals("TEST-001", foundProduct.get().skuCode)
        assertEquals(BigDecimal("99.99"), foundProduct.get().price)
        assertNotNull(foundProduct.get().id)
    }

    @Test
    fun `should find all products`() {
        // Given
        val product1 = ProductDocumentEntity(
            name = "Product 1",
            description = "Description 1",
            skuCode = "PROD-001",
            price = BigDecimal("100.00")
        )
        val product2 = ProductDocumentEntity(
            name = "Product 2",
            description = "Description 2",
            skuCode = "PROD-002",
            price = BigDecimal("200.00")
        )
        productRepository.save(product1)
        productRepository.save(product2)

        // When
        val allProducts = productRepository.findAll()

        // Then
        assertEquals(2, allProducts.size)
        assertTrue(allProducts.any { it.name == "Product 1" && it.skuCode == "PROD-001" })
        assertTrue(allProducts.any { it.name == "Product 2" && it.skuCode == "PROD-002" })
    }

    @Test
    fun `should delete product by id`() {
        // Given
        val product = ProductDocumentEntity(
            name = "To Delete",
            description = "Will be deleted",
            skuCode = "DELETE-001",
            price = BigDecimal("50.00")
        )
        val savedProduct = productRepository.save(product)

        // When
        productRepository.deleteById(savedProduct.id!!)
        val foundProduct = productRepository.findById(savedProduct.id!!)

        // Then
        assertFalse(foundProduct.isPresent)
    }

    @Test
    fun `should return empty when product not found`() {
        // Given
        val nonExistentId = "non-existent-id"

        // When
        val foundProduct = productRepository.findById(nonExistentId)

        // Then
        assertFalse(foundProduct.isPresent)
    }

    @Test
    fun `should update existing product`() {
        // Given
        val originalProduct = ProductDocumentEntity(
            name = "Original Name",
            description = "Original Description",
            skuCode = "ORIG-001",
            price = BigDecimal("100.00")
        )
        val savedProduct = productRepository.save(originalProduct)

        // When
        val updatedProduct = savedProduct.copy(
            name = "Updated Name",
            price = BigDecimal("150.00")
        )
        val result = productRepository.save(updatedProduct)

        // Then
        assertEquals(savedProduct.id, result.id)
        assertEquals("Updated Name", result.name)
        assertEquals(BigDecimal("150.00"), result.price)
        assertEquals("Original Description", result.description) // unchanged
        assertEquals("ORIG-001", result.skuCode) // unchanged
    }
}