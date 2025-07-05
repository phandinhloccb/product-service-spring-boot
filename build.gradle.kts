val generatedResourcesDir = "${layout.buildDirectory.get()}/generated-resources"

plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.5.3"
	id("io.spring.dependency-management") version "1.1.7"
	id("org.openapi.generator") version "7.4.0"
}

group = "com.loc"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("io.swagger.core.v3:swagger-annotations:2.2.15")
	implementation("io.swagger.core.v3:swagger-models:2.2.15")
	
	// SpringDoc OpenAPI for API docs only (no UI)
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-api:2.2.0")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	
	// MockK dependencies
	testImplementation("io.mockk:mockk:1.13.8")
	testImplementation("com.ninja-squad:springmockk:4.0.2")
	testImplementation("org.testcontainers:mongodb:1.19.3")
	testImplementation("org.testcontainers:junit-jupiter:1.19.3")
}

// OpenAPI Generator
openApiGenerate {
	inputSpec.set("$rootDir/src/main/resources/static/openapi.yaml")
	generatorName.set("kotlin-spring")
    modelPackage.set("com.loc.productservice.model")
    outputDir.set("$generatedResourcesDir/openapi")

    configOptions.put("useSpringBoot3", "true")
    configOptions.put("dateLibrary", "java8")
    configOptions.put("delegatePattern", "true")
    configOptions.put("interfaceOnly", "true")
    configOptions.put("modelMutable", "true")
    configOptions.put("useTags", "true")
    configOptions.put("enumPropertyNaming", "UPPERCASE")
    configOptions.put("useBigDecimal", "true")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

sourceSets {
    main {
        kotlin {
            srcDir("$generatedResourcesDir/openapi/src/main/kotlin")
        }
    }
}

tasks.compileKotlin {
    dependsOn(tasks.openApiGenerate)
}

tasks.withType<Test> {
	useJUnitPlatform()
}
