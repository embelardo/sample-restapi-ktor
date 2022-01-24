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

object DeleteSerumSpec : Spek({
    describe("Delete serums") {
        val testEngine = TestApplicationEngine(createTestEnvironment())
        testEngine.start(wait = false)

        val mockSerumService = mockk<SerumService>()

        beforeEachTest {
            clearAllMocks()
        }

        // Mock external dependency
        testEngine.application.moduleWithDependencies(mockSerumService)

        with(testEngine) {
            it("Should delete one serum") {
                every { mockSerumService.delete(0) } returns true
                handleRequest(HttpMethod.Delete, "/api/serums/0").apply {
                    response.status()?.equals(HttpStatusCode.NoContent)
                }
            }
        }
    }
})
