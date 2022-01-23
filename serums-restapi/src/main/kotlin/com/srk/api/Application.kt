package com.srk.api

import com.fasterxml.jackson.databind.SerializationFeature
import com.srk.service.data.SerumServiceImpl
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.response.*
import io.ktor.routing.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    // Install Routing plugin (Allows definition of structured routes and associated handlers)
    install(Routing) {
        trace { application.log.trace(it.buildText()) }
        // Add routes to this API.
        val serumService = SerumServiceImpl()
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
