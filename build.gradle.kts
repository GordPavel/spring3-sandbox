import org.gradle.api.JavaVersion.VERSION_17
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot") version "3.1.2"
    id("io.spring.dependency-management") version "1.1.2"
    id("org.graalvm.buildtools.native") version "0.9.23"
    kotlin("jvm") version "1.8.22"
    kotlin("kapt") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
}

group = "com.example"
version = "0.0.1"

java {
    sourceCompatibility = VERSION_17
}

repositories {
    mavenCentral()
}

val springCloudVersion: String by project

extra["springCloudVersion"] = springCloudVersion

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    val mapstructVersion: String by project
    implementation("org.mapstruct:mapstruct:$mapstructVersion")
    kapt("org.mapstruct:mapstruct-processor:$mapstructVersion")

//    telemetry
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.micrometer:micrometer-tracing-bridge-brave")
    runtimeOnly("io.micrometer:micrometer-registry-prometheus")

//    logging
    val kotlinLoggingVersion: String by project
    implementation("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")

//    database
    val postgresR2dbcDriverVersion: String by project
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
//    runtimeOnly("io.r2dbc:r2dbc-postgresql:$postgresR2dbcDriverVersion")
    implementation("io.r2dbc:r2dbc-postgresql:$postgresR2dbcDriverVersion")
    implementation("org.postgresql:postgresql")
    implementation("com.zaxxer:HikariCP")

//   liquibase
    val liquibaseCoreVersion: String by project
    val liquibaseSlf4jVersion: String by project
    implementation("org.liquibase:liquibase-core:$liquibaseCoreVersion")
    implementation("com.mattbertolini:liquibase-slf4j:$liquibaseSlf4jVersion")

//    kafka
    implementation("org.springframework.kafka:spring-kafka")

//    security
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-security")

//    kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

//    resilience4j
    implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-reactor-resilience4j")
    implementation("io.github.resilience4j:resilience4j-bulkhead")
    implementation("io.github.resilience4j:resilience4j-micrometer")
    implementation("io.github.resilience4j:resilience4j-ratelimiter")
    implementation("org.springframework.boot:spring-boot-starter-aop")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testRuntimeOnly("io.r2dbc:r2dbc-h2")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:kafka")
    testImplementation("org.testcontainers:r2dbc")
}

tasks.withType<BootJar> {
    enabled = true
    archiveFileName.set("application.${archiveExtension.get()}")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
