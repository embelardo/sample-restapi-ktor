package com.srk

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import java.time.LocalDate

fun Routing.srkApi() {
    route("/api") {
        get("srk") {
            call.respond(srkData)
        }
    }
}

val data1 = SrkData(
    "1618989a-7a3b-11ec-90d6-0242ac120003",
    "Serum Worms Sackrider",
    Phase.ALPHA,
    "Felice Mienn",
    0.78,
    LocalDate.of(2022,1, 20)
)

val data2 = SrkData(
    "b8182b68-7a24-11ec-90d6-0242ac120003",
    "Serum Squids Overturf",
    Phase.BETA,
    "Praeda Rakespear",
    0.87,
    LocalDate.of(2022,1, 20)
)

val srkData = listOf(data1, data2)
