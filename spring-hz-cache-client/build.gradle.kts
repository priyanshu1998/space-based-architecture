plugins {
    java
    id("org.springframework.boot") version "4.0.4"
    id("io.spring.dependency-management") version "1.1.7"

    id("com.diffplug.spotless") version "8.6.0"
}

group = "dev.priyanshu"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-hazelcast")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    implementation("tools.jackson.core:jackson-core:3.1.1") // Jackson 3.x
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.18.8")
    testImplementation("org.springframework.boot:spring-boot-starter-hazelcast-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

spotless {
    java {
        importOrder()
        removeUnusedImports()
        googleJavaFormat()
        formatAnnotations()
    }
}