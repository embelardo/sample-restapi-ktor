package com.srk

import com.srk.shared.Phase
import com.srk.shared.Serum
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.time.LocalDate

fun Routing.serumApi() {
    route("/api") {
        get("/serums") {
            call.respond(serums)
        }

        get("/serums/{id}") {
            val id = call.parameters["id"]
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }
            try {
                val serum = serums[id.toInt()]
                call.respond(serum)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        post("/serums") {
            val serum = call.receive<Serum>()
            val newSerum =
                Serum(serums.size, serum.name, serum.phase, serum.tester, serum.potency, serum.testDate)
            serums = serums + newSerum
            call.respond(HttpStatusCode.Created, serums)
        }

        put("/serums/{id}") {
            val id = call.parameters["id"]
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@put
            }

            val foundSerum = serums.getOrNull(id.toInt())
            if (foundSerum == null) {
                call.respond(HttpStatusCode.NotFound)
                return@put
            }

            val serum = call.receive<Serum>()
            serums = serums.filter { it.id != serum.id }
            serums += serum
            call.respond(HttpStatusCode.NoContent)
        }

        delete("/serums/{id}") {
            val id = call.parameters["id"]
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }

            val foundSerum = serums.getOrNull(id.toInt())
            if (foundSerum == null) {
                call.respond(HttpStatusCode.NotFound)
                return@delete
            }

            serums = serums.filter { it.id != id.toInt() }
            call.respond(HttpStatusCode.NoContent)
        }
    }
}

val serum1 = Serum(
    0,
    "Serum Worms Sackrider",
    Phase.ALPHA,
    "Felice Mienn",
    0.78,
    LocalDate.of(2022, 1, 20)
)

val serum2 = Serum(
    1,
    "Serum Squids Overturf",
    Phase.BETA,
    "Praeda Rakespear",
    0.87,
    LocalDate.of(2022, 1, 20)
)

var serums = listOf(serum1, serum2)
