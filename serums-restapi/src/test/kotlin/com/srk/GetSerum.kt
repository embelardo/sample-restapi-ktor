package com.srk

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class GetSerum {
    @Test
    fun getAllSerums() {
        withTestApplication(Application::module) {
            handleRequest(HttpMethod.Get, "/api/serums").apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    @Test
    fun getOneSerum() {
        withTestApplication(Application::module) {
            handleRequest(HttpMethod.Get, "/api/serums/0").apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    @Test
    fun getNonExistentSerum() {
        withTestApplication(Application::module) {
            handleRequest(HttpMethod.Get, "/api/serums/100").apply {
                assertEquals(HttpStatusCode.NotFound, response.status())
            }
        }
    }
}
