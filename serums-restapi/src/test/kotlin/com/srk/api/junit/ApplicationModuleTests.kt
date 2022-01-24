package com.srk.api.junit

import com.srk.di.appModule
import com.srk.service.data.SerumService
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import org.koin.test.inject
import kotlin.test.assertNotNull

class ApplicationModuleTests : KoinTest {
    @Test
    fun `All definitions should be bound`() {
        checkModules {
            modules(appModule)
        }
        stopKoin()
    }

    @Test
    fun `All definitions should be fit-for-purpose`() {
        startKoin {
            modules(appModule)
        }

        val serumService: SerumService by inject()
        assertNotNull(serumService)

        stopKoin()
    }
}
