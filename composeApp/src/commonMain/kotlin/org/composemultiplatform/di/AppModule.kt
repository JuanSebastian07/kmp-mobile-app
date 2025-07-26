package org.composemultiplatform.di

import org.composemultiplatform.domain.use_case.add_expense.AddExpenseUseCase
import org.composemultiplatform.domain.use_case.get_expense.GetExpenseUseCase
import org.composemultiplatform.domain.use_case.get_expenses.GetExpensesUseCase
import org.composemultiplatform.domain.use_case.update_expense.UpdateExpenseUseCase
import org.composemultiplatform.presentation.detail.DetailViewModel
import org.composemultiplatform.presentation.expense.ExpensesViewModel
import org.koin.dsl.module

val appModule = module {

    single { GetExpensesUseCase(get()) }
    single { GetExpenseUseCase(get()) }
    single { AddExpenseUseCase(get()) }
    single { UpdateExpenseUseCase(get()) }
    //single { DeleteExpenseUseCase(get()) }
    factory { ExpensesViewModel(get()) }
    factory { DetailViewModel(get(), get(), get(), get() ) }
}