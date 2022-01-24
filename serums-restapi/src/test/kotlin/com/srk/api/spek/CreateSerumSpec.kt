package com.srk.api.spek

import com.srk.api.moduleWithDependencies
import com.srk.service.data.SerumService
import com.srk.shared.Phase
import com.srk.shared.Serum
import io.ktor.http.*
import io.ktor.server.testing.*
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.time.LocalDate

object CreateSerumSpec : Spek({
    val serum0 = Serum(0, "Serum1", Phase.ALPHA, "Tester1", 25.0, LocalDate.now())
    val serum1 = Serum(1, "Serum2", Phase.BETA, "Tester2", 75.0, LocalDate.now())
    val newSerum = Serum(0, "Serum1", Phase.ALPHA, "Tester1", 25.0, LocalDate.now())

    val newSerumJson =
        """
        {
          "name": "Serum Ovaltine Splern",
          "phase": "ALPHA",
          "tester": "Taki Amre",
          "potency": 0.35,
          "testDate": "2022-01-21"
        }
        """.trimIndent()
    
    describe("Create serums") {
        val testEngine = TestApplicationEngine(createTestEnvironment())
        testEngine.start(wait = false)

        // Mock external dependency
        val mockSerumService = mockk<SerumService>()

        beforeEachTest {
            clearAllMocks()
        }

        testEngine.application.moduleWithDependencies(mockSerumService)

        with(testEngine) {
            it("Should create one serum") {
                every { mockSerumService.create(any()) } returns true
                every { mockSerumService.read() } returns listOf(serum0, serum1, newSerum)
                with(handleRequest(HttpMethod.Post, "/api/serums") {
                    addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                    setBody(newSerumJson)
                }) {
                    response.status()?.equals(HttpStatusCode.OK)
                }
            }
        }
    }
})
