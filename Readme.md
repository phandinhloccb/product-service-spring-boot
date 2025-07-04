# Product Service - Clean Architecture Implementation

A Spring Boot microservice for product management built following Clean Architecture principles, implementing the Hexagonal Architecture pattern with CQRS (Command Query Responsibility Segregation) and proper dependency inversion.

## 📋 Table of Contents

- [Architecture Overview](#architecture-overview)
- [Project Structure](#project-structure)
- [Clean Architecture Layers](#clean-architecture-layers)
- [Key Features](#key-features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [API Documentation](#api-documentation)
- [Testing Strategy](#testing-strategy)
- [CQRS Implementation](#cqrs-implementation)
- [Database Integration](#database-integration)

## 🏗️ Architecture Overview

This project implements Clean Architecture with Hexagonal Architecture pattern and CQRS, following these core principles:

- **Dependency Inversion**: Inner layers define interfaces (ports), outer layers implement them (adapters)
- **Separation of Concerns**: Each layer has a single responsibility
- **Framework Independence**: Business logic is isolated from frameworks
- **CQRS Pattern**: Separate services for Commands (Add) and Queries (List)
- **Testability**: Easy to unit test with proper mocking

### Architecture Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                    Controllers                              │
│              (HTTP/REST API Interface)                      │
│         AddProduct & ListAllProducts                        │
└─────────────────────┬───────────────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────────────┐
│                  Application                                │
│          (Use Cases & Business Rules)                       │
│    AddProductService │ ListAllProductService                │
└─────────────────────┬───────────────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────────────┐
│                    Domain                                   │
│              (Entities & Business Logic)                    │
│                   Product                                   │
└─────────────────────┬───────────────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────────────┐
│                Infrastructure                               │
│         (MongoDB, Adapters, Mappers)                       │
│    ProductRepositoryAdapter │ ProductDocumentEntity        │
└─────────────────────────────────────────────────────────────┘
```

## 📁 Project Structure

```
src/main/kotlin/com/loc/product_service/
├── domain/                          # Enterprise Business Rules
│   └── model/
│       └── Product.kt              # Domain Entity
│
├── application/                     # Application Business Rules
│   ├── service/
│   │   ├── AddProductService.kt    # Command Use Case
│   │   └── ListAllProductService.kt # Query Use Case
│   └── port/
│       └── ProductRepositoryPort.kt # Repository Contract (Port)
│
├── infrastructure/                  # Frameworks & Drivers
│   ├── adapter/
│   │   └── ProductRepositoryAdapter.kt # Repository Implementation (Adapter)
│   ├── repository/
│   │   ├── ProductRepository.kt    # MongoDB Repository Interface
│   │   └── entity/
│   │       └── ProductDocumentEntity.kt # MongoDB Document
│   └── mapper/
│       └── ProductDocumentEntityMapper.kt # Domain ↔ Entity Mapping
│
├── controller/                      # Interface Adapters
│   ├── ProductController.kt        # REST Controller
│   └── mapper/
│       └── AddProductMapper.kt     # DTO ↔ Domain Mapping
│
└── configuration/
    └── BeanConfiguration.kt        # Dependency Injection Configuration
```

## 🎯 Clean Architecture Layers

### 1. Domain Layer (Innermost)
- **Purpose**: Contains enterprise business rules and entities
- **Dependencies**: None (pure business logic)
- **Components**:
  - `Product`: Core domain entity with id, name, description, skuCode, and price
  - Uses `BigDecimal` for precise price calculations
  - Immutable data class following functional programming principles

### 2. Application Layer
- **Purpose**: Contains application-specific business rules (use cases)
- **Dependencies**: Only depends on Domain layer
- **Components**:
  - `AddProductService`: Handles product creation commands
  - `ListAllProductService`: Handles product listing queries
  - `ProductRepositoryPort`: Port (interface) defining data access contract

### 3. Infrastructure Layer (Outermost)
- **Purpose**: Contains frameworks, databases, external services
- **Dependencies**: Implements interfaces from inner layers
- **Components**:
  - `ProductRepositoryAdapter`: Implements domain repository contract
  - `ProductDocumentEntity`: MongoDB document for database persistence
  - `ProductRepository`: Spring Data MongoDB repository
  - `ProductDocumentEntityMapper`: Maps between domain and persistence models

### 4. Controller Layer (Interface Adapters)
- **Purpose**: Handles HTTP requests and responses
- **Dependencies**: Uses Application layer services
- **Components**:
  - `ProductController`: REST API endpoints
  - `AddProductMapper`: Maps between DTOs and domain models

## ✨ Key Features

- **Clean Architecture Implementation**: Proper dependency inversion and layer separation
- **CQRS Pattern**: Separate services for commands and queries
- **Hexagonal Architecture**: Ports and Adapters pattern implementation
- **MongoDB Integration**: NoSQL database with Spring Data MongoDB
- **OpenAPI Integration**: Auto-generated API documentation
- **Comprehensive Testing**: Unit and integration tests for all layers
- **Type Safety**: Kotlin with proper null safety and BigDecimal for monetary values

## 🛠️ Technologies Used

- **Framework**: Spring Boot 3.5.3
- **Language**: Kotlin 1.9.25
- **Database**: MongoDB 8.0.6
- **Documentation**: OpenAPI 3.0 Generator
- **Testing**: JUnit 5, MockK, TestContainers
- **Build Tool**: Gradle with Kotlin DSL
- **Runtime**: Java 17

## 🚀 Getting Started

### Prerequisites

- JDK 17 or higher
- Docker and Docker Compose
- Gradle 7.x or higher

### Running the Application

1. **Start MongoDB**:
   ```bash
   cd server
   docker-compose up -d
   ```

2. **Run the Application**:
   ```bash
   ./gradlew bootRun
   ```

3. **Access the API**:
   - Application: http://localhost:8080
   - MongoDB: localhost:27017 (admin/password)

### Configuration

The application uses `application.properties` for configuration:

```properties
spring.application.name=product-service
server.port=8080

# MongoDB Configuration
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=product-service
spring.data.mongodb.username=admin
spring.data.mongodb.password=password
spring.data.mongodb.authentication-database=admin

# Disable SQL DataSource auto-configuration
spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
```

## 📚 API Documentation

### Product Management Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/product/add` | Add a new product |
| GET | `/api/product/all` | Get all products |

### Request/Response Examples

**Add Product**:
```json
POST /api/product/add
Content-Type: application/json

{
  "name": "iPhone 15 Pro",
  "description": "Latest iPhone Pro model",
  "skuCode": "IPHONE15PRO",
  "price": 1199.99
}
```

**Response**:
```json
{
  "name": "iPhone 15 Pro",
  "description": "Latest iPhone Pro model",
  "skuCode": "IPHONE15PRO",
  "price": 1199.99
}
```

**List All Products**:
```json
GET /api/product/all

Response:
[
  {
    "id": "507f1f77bcf86cd799439011",
    "name": "iPhone 15 Pro",
    "description": "Latest iPhone Pro model",
    "skuCode": "IPHONE15PRO",
    "price": 1199.99
  },
  {
    "id": "507f1f77bcf86cd799439012",
    "name": "MacBook Pro",
    "description": "Professional laptop",
    "skuCode": "MACBOOK-PRO-16",
    "price": 2499.00
  }
]
```

## 🧪 Testing Strategy

### Test Structure Following Clean Architecture

```
src/test/kotlin/com/loc/product_service/
├── application/service/
│   ├── AddProductServiceTest.kt           # Command Use Case Tests
│   └── ListAllProductServiceTest.kt       # Query Use Case Tests
├── controller/
│   └── ProductControllerTest.kt           # API Integration Tests
└── infrastructure/
    ├── adapter/
    │   └── ProductRepositoryAdapterTest.kt # Repository Adapter Tests
    ├── mapper/
    │   └── ProductDocumentEntityMapperTest.kt # Entity Mapping Tests
    └── repository/
        └── ProductRepositoryTest.kt        # MongoDB Repository Tests
```

### Running Tests

```bash
# Run all tests
./gradlew test

# Run specific test categories
./gradlew test --tests "*Controller*"
./gradlew test --tests "*Service*"
./gradlew test --tests "*Repository*"

# Run with coverage
./gradlew test jacocoTestReport
```

### Test Technologies

- **Unit Tests**: MockK for mocking dependencies
- **Integration Tests**: TestContainers for MongoDB integration
- **Web Layer Tests**: @WebMvcTest with SpringMockK
- **Repository Tests**: @DataMongoTest with embedded MongoDB

## 🔄 CQRS Implementation

The service implements CQRS pattern with separate services for commands and queries:

### Command Side (AddProductService)
```kotlin
@Service
class AddProductService(
    private val productRepositoryPort: ProductRepositoryPort
) {
    fun addProduct(product: Product): Product {
        return productRepositoryPort.addProduct(product)
    }
}
```

### Query Side (ListAllProductService)
```kotlin
@Service
class ListAllProductService(
    private val productRepository: ProductRepositoryPort
) {
    fun listAllProducts(): List<Product> {
        return productRepository.findAll()
    }
}
```

### Controller Integration
```kotlin
@RestController
@RequestMapping("/api/product")
class ProductController(
    private val addProductService: AddProductService,
    private val listAllProductService: ListAllProductService
) {
    @PostMapping("/add")
    fun addProduct(@RequestBody request: AddProductRequest): ResponseEntity<ProductResponse> {
        // Command handling
    }
    
    @GetMapping("/all")
    fun listAllProducts(): ResponseEntity<List<ProductResponse>> {
        // Query handling
    }
}
```

## 🗄️ Database Integration

### MongoDB Document Structure

```kotlin
@Document(collection = "products")
data class ProductDocumentEntity(
    @Id val id: String? = null,
    val name: String,
    val description: String,
    val skuCode: String,
    val price: BigDecimal
)
```

### Repository Pattern Implementation

```kotlin
// Port (Interface)
interface ProductRepositoryPort {
    fun addProduct(product: Product): Product
    fun findAll(): List<Product>
}

// Adapter (Implementation)
@Component
class ProductRepositoryAdapter(
    private val productRepository: ProductRepository
) : ProductRepositoryPort {
    override fun addProduct(product: Product): Product {
        val document = product.toProductDocumentEntity()
        val savedDocument = productRepository.save(document)
        return savedDocument.toProduct()
    }
    
    override fun findAll(): List<Product> {
        return productRepository.findAll().map { it.toProduct() }
    }
}
```

## 🔧 Dependency Injection Configuration

```kotlin
@Configuration
class BeanConfiguration {
    @Bean
    fun productRepositoryPort(productRepository: ProductRepository): ProductRepositoryPort {
        return ProductRepositoryAdapter(productRepository)
    }
}
```

## 🏆 Clean Architecture Benefits Achieved

1. **Independence**: Business logic is independent of frameworks, UI, and external agencies
2. **Testability**: Easy to test business rules without UI, database, or external services
3. **Flexibility**: Easy to change frameworks, databases, or external services
4. **Maintainability**: Clear separation of concerns makes the code easier to understand and modify
5. **Scalability**: Architecture supports scaling individual components independently
6. **CQRS Benefits**: Optimized read and write operations with separate models
7. **Type Safety**: Kotlin's type system prevents common runtime errors
8. **Monetary Precision**: BigDecimal ensures accurate financial calculations

## 📊 Architecture Validation

The project validates Clean Architecture principles through:

- **Dependency Rule**: Dependencies point inward only
- **Port-Adapter Pattern**: Clear interfaces between layers
- **CQRS Separation**: Distinct command and query responsibilities
- **Domain Isolation**: Pure business logic without framework dependencies
- **Comprehensive Testing**: Each layer tested independently
- **Framework Independence**: Business logic can be tested without Spring Boot

This implementation demonstrates how Clean Architecture principles can be effectively applied to a Spring Boot microservice, resulting in a maintainable, testable, and scalable codebase.
