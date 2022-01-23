package com.srk.api

import com.fasterxml.jackson.databind.SerializationFeature
import com.srk.di.appModule
import com.srk.service.data.SerumService
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    // Install Koin plugin (Allows dependency injection)
    install(Koin) {
        modules(appModule)
    }

    // Inject dependency
    val serumService: SerumService by inject()
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
        this.exception<Throwable> { e ->
            call.respondText(e.localizedMessage, ContentType.Text.Plain, HttpStatusCode.InternalServerError)
            throw e
        }
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
