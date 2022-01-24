package com.srk.api.junit

import com.srk.api.module
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ReadSerum {
    @Test
    fun `Read all serums`() {
        withTestApplication(Application::module) {
            handleRequest(HttpMethod.Get, "/api/serums").apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    @Test
    fun `Read one serum`() {
        withTestApplication(Application::module) {
            handleRequest(HttpMethod.Get, "/api/serums/0").apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    @Test
    fun `Read non-existent serum`() {
        withTestApplication(Application::module) {
            handleRequest(HttpMethod.Get, "/api/serums/100").apply {
                assertEquals(HttpStatusCode.NotFound, response.status())
            }
        }
    }
}
