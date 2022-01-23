val jacksonVersion: String by project
val junit5Version: String by project
val koinVersion: String by project
val kotlinVersion: String by project
val ktorVersion: String by project
val logbackVersion: String by project
val mockkVersion: String by project

plugins {
    application
    kotlin("jvm") version "1.6.10"
}

repositories {
    mavenCentral()
}

group = "com.srk"
version = "0.0.1"
application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

dependencies {
    // Gradle sub-project dependencies
    implementation(project(":serums-shared"))
    implementation(project(":serums-service-data"))

    // Ktor server engine (Netty, Jetty, Tomcat, or CIO (Coroutine-based I/O))
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-host-common:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")

    // Logback logging framework
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    // Jackson JSON library
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")
    implementation("io.ktor:ktor-jackson:$ktorVersion")

    // Koin for Ktor applications
    implementation("io.insert-koin:koin-ktor:$koinVersion")
    implementation("io.insert-koin:koin-logger-slf4j:$koinVersion")
    // Koin Core features
    implementation("io.insert-koin:koin-core:$koinVersion")
    // Koin Test features
    testImplementation("io.insert-koin:koin-test:$koinVersion")
    // Koin for JUnit
    testImplementation("io.insert-koin:koin-test-junit4:$koinVersion")
    testImplementation("io.insert-koin:koin-test-junit5:$koinVersion")

    // JUnit5 unit-testing framework for developer-side testing on JVM
    testImplementation("org.junit.jupiter:junit-jupiter:$junit5Version")

    // Ktor test engine for direct call processing (no web server, bound sockets, or real HTTP request)
    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")

    // MockK mocking library for Kotlin
    testImplementation("io.mockk:mockk:$mockkVersion")
}

tasks.withType<Test> {
    useJUnitPlatform {
        includeEngines("junit-jupiter")
    }
}
