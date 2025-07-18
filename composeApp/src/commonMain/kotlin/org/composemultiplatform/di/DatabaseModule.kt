package org.composemultiplatform.di

import com.expenseApp.database.AppDatabase
import org.composemultiplatform.data.local.DatabaseDriverFactory
import org.composemultiplatform.data.repository.ExpenseRepositoryImpl
import org.composemultiplatform.domain.repository.ExpenseRepository
import org.koin.dsl.module

val databaseModule = module {
    single<AppDatabase> {
        val driverFactory = get<DatabaseDriverFactory>()
        AppDatabase(driverFactory.createDriver())
    }
    single<ExpenseRepository> { ExpenseRepositoryImpl(get(), get()) }
}