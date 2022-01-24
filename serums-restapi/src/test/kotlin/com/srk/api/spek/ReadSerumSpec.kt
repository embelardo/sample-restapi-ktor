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

object ReadSerumSpec : Spek({
    val serum0 = Serum(0, "Serum1", Phase.ALPHA, "Tester1", 25.0, LocalDate.now())
    val serum1 = Serum(1, "Serum2", Phase.BETA, "Tester2", 75.0, LocalDate.now())

    describe("Read serums") {
        val testEngine = TestApplicationEngine(createTestEnvironment())
        testEngine.start(wait = false)

        val mockSerumService = mockk<SerumService>()

        beforeEachTest {
            clearAllMocks()
        }

        // Mock external dependency
        testEngine.application.moduleWithDependencies(mockSerumService)

        with(testEngine) {
            it("Should return all serums") {
                every { mockSerumService.read() } returns listOf(serum0, serum1)
                handleRequest(HttpMethod.Get, "/api/serums").apply {
                    response.status()?.equals(HttpStatusCode.OK)
                    response.content?.contains(Regex("Serum[01]"))
                }
            }

            it("Should return one serum") {
                every { mockSerumService.read(0) } returns serum0
                handleRequest(HttpMethod.Get, "/api/serums/0").apply {
                    response.status()?.equals(HttpStatusCode.OK)
                    response.content?.contains(Regex("Serum0"))
                }
            }

            it("Should reply with not found") {
                every { mockSerumService.read(100) } returns null
                handleRequest(HttpMethod.Get, "/api/serums/100").apply {
                    response.status()?.equals(HttpStatusCode.NotFound)
                }
            }
        }
    }
})
