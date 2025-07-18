package org.composemultiplatform.di

import org.composemultiplatform.presentation.expense.ExpensesViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

expect val platformPresentationModule : Module

val presentationModule = module{
    viewModelOf(::ExpensesViewModel)
}