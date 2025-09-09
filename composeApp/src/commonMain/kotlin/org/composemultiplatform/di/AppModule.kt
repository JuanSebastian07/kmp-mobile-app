package org.composemultiplatform.di

import org.composemultiplatform.domain.use_case.add_expense.AddExpenseUseCase
import org.composemultiplatform.domain.use_case.delete_expense.DeleteExpenseUseCase
import org.composemultiplatform.domain.use_case.get_current_user.GetCurrentUserUseCase
import org.composemultiplatform.domain.use_case.get_expense.GetExpenseUseCase
import org.composemultiplatform.domain.use_case.get_expenses.GetExpensesUseCase
import org.composemultiplatform.domain.use_case.signIn_with_email.SignInWithEmailUseCase
import org.composemultiplatform.domain.use_case.update_expense.UpdateExpenseUseCase
import org.composemultiplatform.presentation.app.AppViewModel
import org.composemultiplatform.presentation.detail.DetailViewModel
import org.composemultiplatform.presentation.expense.ExpensesViewModel
import org.composemultiplatform.presentation.sign_in.SignInViewModel
import org.koin.dsl.module

val appModule = module {

    single { GetExpensesUseCase(get()) }
    single { GetExpenseUseCase(get()) }
    single { AddExpenseUseCase(get()) }
    single { UpdateExpenseUseCase(get()) }
    single { DeleteExpenseUseCase(get()) }
    single { SignInWithEmailUseCase(get()) }
    single { GetCurrentUserUseCase(get()) }
    factory { ExpensesViewModel(get(), get() ) }
    factory { SignInViewModel(get()) }
    factory { AppViewModel(get()) }
    factory { DetailViewModel(get(), get(), get(), get() ) }
}