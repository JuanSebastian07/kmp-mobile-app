package org.composemultiplatform.di

import org.composemultiplatform.data.ExpenseManager
import org.composemultiplatform.data.repository.ExpenseRepositoryImpl
import org.composemultiplatform.domain.repository.ExpenseRepository
import org.composemultiplatform.domain.use_case.GetExpensesUseCase
import org.composemultiplatform.presentation.expense.ExpensesViewModel
import org.koin.core.module.dsl.createdAtStart
import org.koin.core.module.dsl.withOptions
import org.koin.dsl.module

val appModule = module {

    single { ExpenseManager }.withOptions { createdAtStart() }
    single { GetExpensesUseCase(get()) }
    //single { AddExpenseUseCase(get()) }
    //single { DeleteExpenseUseCase(get()) }
    factory { ExpensesViewModel(get()) }
}