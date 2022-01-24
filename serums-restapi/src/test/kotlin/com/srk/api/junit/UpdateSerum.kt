package com.srk.api.junit

import com.srk.api.module
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class UpdateSerum {
    @Test
    fun `Update serum`() = withTestApplication(Application::module) {
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
