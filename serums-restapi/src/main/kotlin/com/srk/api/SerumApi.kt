package com.srk.api

import com.srk.service.data.SerumService
import com.srk.shared.Serum
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Routing.serumApi(serumService: SerumService) {
    route("/api") {
        get("/serums") {
            call.respond(serumService.read())
        }

        get("/serums/{id}") {
            call.parameters["id"]
                ?.let { id ->
                    try {
                        serumService.read(id.toInt())
                            ?.let { serum ->
                                call.respond(serum)
                            } ?: call.respond(HttpStatusCode.NotFound)
                    } catch (e: NumberFormatException) {
                        call.respond(HttpStatusCode.BadRequest)
                    }
                }
                ?: run {
                    call.respond(HttpStatusCode.NotFound)
                }
        }

        post("/serums") {
            val newSerum = call.receive<Serum>()
            serumService.create(newSerum)
            call.respond(HttpStatusCode.Created, serumService.read())
        }

        put("/serums/{id}") {
            call.parameters["id"]
                ?.let { id ->
                    try {
                        val updatedSerum = call.receive<Serum>()
                        serumService.update(id.toInt(), updatedSerum)
                        call.respond(HttpStatusCode.NoContent)
                    } catch (e: NumberFormatException) {
                        call.respond(HttpStatusCode.BadRequest)
                    }
                }
                ?: run {
                    call.respond(HttpStatusCode.BadRequest)
                }
        }

        delete("/serums/{id}") {
            call.parameters["id"]
                ?.let { id ->
                    try {
                        serumService.delete(id.toInt())
                        call.respond(HttpStatusCode.NoContent)
                    } catch (e: NumberFormatException) {
                        call.respond(HttpStatusCode.BadRequest)
                    }
                }
                ?: run {
                    call.respond(HttpStatusCode.BadRequest)
                }
        }
    }
}
