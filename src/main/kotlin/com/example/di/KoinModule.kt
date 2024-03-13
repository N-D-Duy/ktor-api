package com.example.di

import com.example.repositories.ImageRepository
import com.example.repositories.ImageRepositoryImpl
import com.example.usecases.*
import com.example.utils.database.DatabaseFactory
import com.example.utils.database.DatabaseFactoryImpl
import com.example.utils.database.DbTransaction
import com.example.utils.database.DbTransactionImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.KoinApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.core.module.Module

fun KoinApplication.configure(koinModules: List<Module>) {
    allowOverride(true)
    modules(koinModules)
}

fun koinModules() = listOf(mainModule, coroutinesModule, providers, useCases, repositories)


private val coroutinesModule = module {
    single<CoroutineDispatcher>(named("IODispatcher")) { Dispatchers.IO }
    single<CoroutineDispatcher>(named("DefaultDispatcher")) { Dispatchers.Default }
    single<CoroutineDispatcher>(named("Main")) { Dispatchers.Main }
}

private val providers = module {
    single<ImageRepoProvider> { ImageRepoProviderImpl(get()) }
}

private val useCases = module {
    factory<ImagesUseCase> { ImagesUseCasesImpl(get(named("IODispatcher")), get()) }
}

private val repositories = module {
    single<ImageRepository> { ImageRepositoryImpl(get()) }
}

private val mainModule = module {
    single<DatabaseFactory> { params ->
        DatabaseFactoryImpl(config = params.get())
    }
    single<DbTransaction> {
        DbTransactionImpl(get(named("IODispatcher")))
    }
}


