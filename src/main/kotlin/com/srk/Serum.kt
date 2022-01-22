package com.srk

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import java.time.LocalDate

data class Serum(
    val id: Int,
    val name: String,
    val phase: Phase,
    val tester: String,
    val potency: Double,
    // Serialization hint
    @JsonSerialize(using = ToStringSerializer::class)
    // Deserialization hint to simplify date format in response
    @JsonDeserialize(using = LocalDateDeserializer::class)
    val testDate: LocalDate
)

enum class Phase {
    ALPHA, BETA, GAMMA, DELTA, EPSILON, OMEGA
}
