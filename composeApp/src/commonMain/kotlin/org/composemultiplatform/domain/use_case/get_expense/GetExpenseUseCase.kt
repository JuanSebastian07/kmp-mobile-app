package org.composemultiplatform.domain.use_case.get_expense

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.composemultiplatform.core.UiState
import org.composemultiplatform.domain.model.Expense
import org.composemultiplatform.domain.repository.ExpenseRepository

class GetExpenseUseCase(
    private val expenseRepository: ExpenseRepository
) {
    operator fun invoke(id: Long) : Flow<UiState<Expense>> = flow {
        try {
            emit(UiState.Loading())
            val expense = expenseRepository.getExpenseById(id)
            emit(UiState.Success(expense))
            } catch (e: Exception) {
            emit(UiState.Error(e.message ?: "Error"))
        }
    }
}