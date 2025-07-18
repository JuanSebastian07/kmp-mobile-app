package org.composemultiplatform.di

import org.composemultiplatform.data.local.AndroidDatabaseDriverFactory
import org.composemultiplatform.data.local.DatabaseDriverFactory
import org.koin.dsl.module

actual val platformPresentationModule = module {
    single<DatabaseDriverFactory> { AndroidDatabaseDriverFactory(get()) }
}