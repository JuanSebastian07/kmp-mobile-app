package org.composemultiplatform.di

import org.composemultiplatform.data.local.DatabaseDriverFactory
import org.composemultiplatform.data.local.IOSDatabaseDriverFactory
import org.composemultiplatform.data.repository.AuthRepositoryIosImpl
import org.composemultiplatform.domain.repository.AuthRepository
import org.koin.dsl.module

actual val platformPresentationModule = module {
    single<DatabaseDriverFactory> { IOSDatabaseDriverFactory()  }
    single<AuthRepository>{ AuthRepositoryIosImpl() }
}