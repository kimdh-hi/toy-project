import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.konan.properties.loadProperties

plugins {
  id("org.springframework.boot") version "2.7.0"
  id("io.spring.dependency-management") version "1.0.11.RELEASE"
  kotlin("jvm") version "1.6.21"
  kotlin("plugin.spring") version "1.6.21"
  kotlin("plugin.jpa") version "1.6.21"
  kotlin("plugin.noarg") version "1.6.21"
}

group = "com.toy"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
  mavenCentral()
}

allOpen {
  annotation("javax.persistence.Entity")
  annotation("javax.persistence.Embeddable")
  annotation("javax.persistence.MappedSuperclass")
}

noArg {
  annotation("javax.persistence.Entity")
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
  implementation("org.springframework.boot:spring-boot-starter-web")

  implementation("org.springframework.boot:spring-boot-starter-mail")
  implementation("org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE")
  implementation("io.awspring.cloud:spring-cloud-aws-ses:2.4.1")
  implementation("software.amazon.awssdk:ses:2.17.204")

  annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

  implementation("org.webjars:bootstrap:4.6.1")
  implementation("org.webjars:jquery:3.5.1")

  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  runtimeOnly("com.h2database:h2")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "17"
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}

fun loadCustomProperties(): MutableMap<String, String> {
  val customProperties = "secret-properties.properties"

  val map = mutableMapOf<String, String>()
  loadProperties(customProperties).forEach { entry ->
    map.put(entry.key as String, entry.value as String)
  }

  return map
}

tasks.processResources {
  val properties = loadCustomProperties()

  filesMatching("**/application.yml") {
    expand(properties)
  }
}
