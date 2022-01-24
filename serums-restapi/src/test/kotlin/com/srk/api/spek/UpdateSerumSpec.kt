package com.srk.api.spek

import com.srk.api.moduleWithDependencies
import com.srk.service.data.SerumService
import io.ktor.http.*
import io.ktor.server.testing.*
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object UpdateSerumSpec : Spek({
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

    describe("Update serums") {
        val testEngine = TestApplicationEngine(createTestEnvironment())
        testEngine.start(wait = false)

        // Mock external dependency
        val mockSerumService = mockk<SerumService>()

        beforeEachTest {
            clearAllMocks()
        }

        testEngine.application.moduleWithDependencies(mockSerumService)

        with(testEngine) {
            it("Should update one serum") {
                every { mockSerumService.update(1, any()) } returns true
                with(handleRequest(HttpMethod.Put, "/api/serums/1") {
                    addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                    setBody(updatedSerum)
                }) {
                    response.status()?.equals(HttpStatusCode.OK)
                }
            }
        }
    }
})
