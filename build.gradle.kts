plugins {
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.4"
    id("java")
}

java {
    // Define a versão do Java (21, ou a que você precisar)
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

group = "dio.task_management"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")

    implementation("mysql:mysql-connector-java:8.0.33")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<JavaCompile>().configureEach {
    options.annotationProcessorPath = configurations.annotationProcessor.get()
}
