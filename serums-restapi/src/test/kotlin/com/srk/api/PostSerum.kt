package com.srk.api

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PostSerum {
    @Test
    fun postSerum() = withTestApplication(Application::module) {
        with(handleRequest(HttpMethod.Post, "/api/serums") {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(newSerum)
        }) {
            assertEquals(HttpStatusCode.Created, response.status())
        }
    }
}

val newSerum =
    """
    {
      "name": "Serum Ovaltine Splern",
      "phase": "ALPHA",
      "tester": "Taki Amre",
      "potency": 0.35,
      "testDate": "2022-01-21"
    }
    """.trimIndent()
