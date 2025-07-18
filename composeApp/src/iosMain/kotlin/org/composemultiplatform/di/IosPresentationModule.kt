package org.composemultiplatform.di

import org.composemultiplatform.data.local.DatabaseDriverFactory
import org.composemultiplatform.data.local.IOSDatabaseDriverFactory
import org.koin.dsl.module

actual val platformPresentationModule = module {
    single<DatabaseDriverFactory> { IOSDatabaseDriverFactory()  }
}