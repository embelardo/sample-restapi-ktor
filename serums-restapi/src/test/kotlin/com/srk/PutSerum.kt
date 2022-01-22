package com.srk

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PutSerum {
    @Test
    fun putSerum() = withTestApplication(Application::module) {
        with(handleRequest(HttpMethod.Put, "/api/serums/1") {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(updatedSerum)
        }) {
            assertEquals(HttpStatusCode.NoContent, response.status())
        }
    }
}

val updatedSerum =
    """
    {
      "id" : 1,
      "name" : "Serum Squids Overturf",
      "phase" : "GAMMA",
      "tester" : "Praeda Rakespear",
      "potency" : 0.95,
      "testDate" : "2022-01-21"
    }
    """.trimIndent()
