package com.klavs.wherecaniwatch.di

import com.klavs.wherecaniwatch.data.datasource.wciwservice.WCIWServiceDatasource
import com.klavs.wherecaniwatch.ktor.KtorClient
import com.klavs.wherecaniwatch.ktor.KtorService
import com.klavs.wherecaniwatch.data.datasource.wciwservice.WCIWServiceDatasourceImpl
import com.klavs.wherecaniwatch.data.repository.wciwservice.WCIWServiceRepository
import com.klavs.wherecaniwatch.data.repository.wciwservice.WCIWServiceRepositoryImpl
import com.klavs.wherecaniwatch.viewmodel.HomeViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single { KtorClient.getClient() }
    singleOf(::KtorService)
    singleOf(::WCIWServiceDatasourceImpl){bind<WCIWServiceDatasource>()}
    singleOf(::WCIWServiceRepositoryImpl){bind<WCIWServiceRepository>()}
    viewModelOf(::HomeViewModel)
}