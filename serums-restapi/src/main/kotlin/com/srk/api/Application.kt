package com.srk.api

import com.fasterxml.jackson.databind.SerializationFeature
import com.srk.service.data.SerumService
import com.srk.service.data.SerumServiceImpl
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.slf4j.LoggerFactory
import java.io.File
import java.security.KeyStore

fun main() {
    val keystoreFile = File("certs/server-keystore.jks")
    val serverPassword = "server-secret"
    val keystore = KeyStore.getInstance("JKS")
    keystore.load(keystoreFile.inputStream(), serverPassword.toCharArray())

//    val truststoreFile = File("certs/client-keystore.jks")
//    val clientPassword = "client-secret"
//    val truststore = KeyStore.getInstance("JKS")
//    truststore.load(truststoreFile.inputStream(), clientPassword.toCharArray())

    val environment = applicationEngineEnvironment {
        developmentMode = false
        log = LoggerFactory.getLogger("serums-restapi")
        connector {
            port = 8081
        }
        sslConnector(
            keyStore = keystore,
            keyAlias = "serumsapi",
            keyStorePassword = { serverPassword.toCharArray() },
            privateKeyPassword = { serverPassword.toCharArray() }
        ) {
            port = 8082
            keyStorePath = keystoreFile
//            trustStore = truststore
//            trustStorePath = keystoreFile
        }
        module(Application::module)
    }

    embeddedServer(Netty, environment).start(wait = true)
}

fun Application.module() {
    val serumService = SerumServiceImpl()
    moduleWithDependencies(serumService)
}

fun Application.moduleWithDependencies(serumService: SerumService) {
    // Install Routing plugin (Allows definition of structured routes and associated handlers)
    install(Routing) {
        // Enable route-resolution tracing
        trace { application.log.trace(it.buildText()) }

        // Add routes to this API.
        serumApi(serumService)
    }

    // Install Status Pages plugin (Allows response to thrown application exceptions)
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = cause.localizedMessage, status = HttpStatusCode.InternalServerError)
        }
        status(HttpStatusCode.NotFound) { call, status ->
            call.respondText(text = "404: Page Not Found", status = status)
        }
        statusFile(HttpStatusCode.Unauthorized, HttpStatusCode.PaymentRequired, filePattern = "error#.html")
    }

    // Install ContentNegotiation plugin (Allows negotiation of media types between client and server)
    install(ContentNegotiation) {
        // Use Jackson library as JSON converter for outgoing Kotlin types
        jackson {
            // Make json human-readable
            enable(SerializationFeature.INDENT_OUTPUT)
            deactivateDefaultTyping()
        }
    }
}
