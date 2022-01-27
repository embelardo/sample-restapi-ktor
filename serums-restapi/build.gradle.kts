val hopliteVersion: String by project
val jacksonVersion: String by project
val junit5Version: String by project
val kluentVersion: String by project
val koinVersion: String by project
val kotlinVersion: String by project
val ktorVersion: String by project
val logbackVersion: String by project
val mockkVersion: String by project
val spekVersion: String by project

plugins {
    application
    kotlin("jvm") version "1.6.10"
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

group = "com.srk"
version = "0.0.1"
application {
    mainClass.set("com.srk.api.ApplicationKt")
}

dependencies {
    // Gradle sub-project dependencies
    implementation(project(":serums-shared"))
    implementation(project(":serums-service-data"))

    // Ktor server engine (Netty, Jetty, Tomcat, or CIO (Coroutine-based I/O))
//    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
//    implementation("io.ktor:ktor-network-tls-certificates:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-server-status-pages:$ktorVersion")
//    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-host-common:$ktorVersion")
//    implementation("io.ktor:ktor-server-netty:$ktorVersion")

    // Logback logging framework
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    // Jackson JSON library
//    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")
//    implementation("io.ktor:ktor-jackson:2.0.0-eap-106")
    implementation("io.ktor:ktor-serialization-jackson:$ktorVersion")

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

    // Hoplite for configuration files
    implementation("com.sksamuel.hoplite:hoplite-core:$hopliteVersion")

    // JUnit5 unit-testing framework for developer-side testing on JVM
    testImplementation("org.junit.jupiter:junit-jupiter:$junit5Version")

    // Ktor test engine for direct call processing (no web server, bound sockets, or real HTTP request)
    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")

    // Spek test framework
    testImplementation("org.spekframework.spek2:spek-dsl-jvm:$spekVersion")
    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:$spekVersion")

    // MockK mocking library for Kotlin
    testImplementation("io.mockk:mockk:$mockkVersion")
    
    // Ktor library for generating self-signed TLS certificate (for test-purposes)
    implementation("io.ktor:ktor-network-tls-certificates:$ktorVersion")
}

tasks.withType<Test> {
    useJUnitPlatform {
        includeEngines("junit-jupiter", "spek2")
    }
}
