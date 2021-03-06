import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
}

group = "com.toy"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security:2.7.1")
    implementation("com.nimbusds:nimbus-jose-jwt:9.23")
    implementation("com.google.crypto.tink:tink:1.6.1")
    implementation("org.springdoc:springdoc-openapi-webflux-ui:1.6.9")
    implementation("io.projectreactor.tools:blockhound:1.0.6.RELEASE")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-debug")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.reactivestreams:reactive-streams:1.0.4")


    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    implementation("org.flywaydb:flyway-core")
    runtimeOnly("com.h2database:h2")
    implementation("io.r2dbc:r2dbc-h2")
    runtimeOnly("org.postgresql:postgresql")
    implementation("org.postgresql:r2dbc-postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.3")
//    testImplementation("io.projectreactor.tools:blockhound-junit-platform:1.0.6.RELEASE")
//    testImplementation("org.junit.platform:junit-platform-launcher:1.8.0-M1")
    testImplementation("com.google.auto.service:auto-service:1.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    if (JavaVersion.current() >= JavaVersion.VERSION_13) {
        jvmArgs = listOf("-XX:+AllowRedefinitionToAddDeleteMethods")
    }
}