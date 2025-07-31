package org.composemultiplatform.domain.use_case.delete_expense

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.composemultiplatform.core.UiState
import org.composemultiplatform.domain.repository.ExpenseRepository

class DeleteExpenseUseCase(
    private val expenseRepository: ExpenseRepository
) {
    operator fun invoke(id: String) : Flow<UiState<Unit>> = flow  {
        try {
            emit(UiState.Loading())
            expenseRepository.deleteExpense(id)
            emit(UiState.Success(Unit))
        }catch (e : Exception) {
            emit(UiState.Error(e.message ?: "Error deleting expense"))
        }
    }
}