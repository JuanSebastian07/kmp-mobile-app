package org.composemultiplatform.domain.use_case.get_expenses

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.composemultiplatform.core.UiState
import org.composemultiplatform.domain.model.Expense
import org.composemultiplatform.domain.repository.ExpenseRepository

class GetExpensesUseCase(
    private val expenseRepository: ExpenseRepository
) {
    operator fun invoke() : Flow<UiState<List<Expense>>> = flow {
        try {
            emit(UiState.Loading())
            val expenses = expenseRepository.getExpenses()
            emit(UiState.Success(expenses))
        } catch (e: Exception) {
            emit(UiState.Error(e.message ?: "Error"))
        }
    }
}