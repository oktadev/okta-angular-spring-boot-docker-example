import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.3.0.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    id("se.patrikerdes.use-latest-versions") version "0.2.13"
    id("com.github.ben-manes.versions") version "0.28.0"
    kotlin("jvm") version "1.3.61"
    kotlin("plugin.spring") version "1.3.61"
    kotlin("plugin.jpa") version "1.3.61"
}

group = "com.okta.developer"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.okta.spring:okta-spring-boot-starter:1.4.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    if (project.hasProperty("prod")) {
        runtimeOnly("org.postgresql:postgresql")
    } else {
        runtimeOnly("com.h2database:h2")
    }
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

val profile = if (project.hasProperty("prod")) "prod" else "dev"

tasks.bootRun {
    args("--spring.profiles.active=${profile}")
}

tasks.processResources {
    rename("application-${profile}.properties", "application.properties")
}
