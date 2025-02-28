plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.2.3"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
}
