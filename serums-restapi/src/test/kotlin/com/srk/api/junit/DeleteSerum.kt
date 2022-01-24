package com.srk.api.junit

import com.srk.api.module
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DeleteSerum {
    @Test
    fun `Delete serum`() {
        withTestApplication(Application::module) {
            handleRequest(HttpMethod.Delete, "/api/serums/0").apply {
                assertEquals(HttpStatusCode.NoContent, response.status())
            }
        }
    }
}
