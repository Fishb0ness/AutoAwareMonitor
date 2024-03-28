plugins {
    java
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "com.fishb0ness"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb:3.2.3")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mockito:mockito-core:4.2.0")
    testImplementation("org.assertj:assertj-core:3.21.0")
    testImplementation("io.rest-assured:rest-assured:5.4.0")
    testImplementation("junit:junit:4.13.1")
    testImplementation("io.rest-assured:spring-mock-mvc:5.4.0")
    testImplementation("org.testcontainers:mongodb:1.19.7")
    implementation("org.apache.commons:commons-compress:1.26.1")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
