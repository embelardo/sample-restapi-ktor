package com.srk.di

import com.srk.service.data.SerumService
import com.srk.service.data.SerumServiceImpl
import org.koin.dsl.module

val appModule = module {
    single<SerumService> { SerumServiceImpl() }
}
