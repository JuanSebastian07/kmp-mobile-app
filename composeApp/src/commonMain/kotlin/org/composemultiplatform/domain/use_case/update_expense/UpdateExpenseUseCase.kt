package org.composemultiplatform.domain.use_case.update_expense

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.composemultiplatform.core.UiState
import org.composemultiplatform.domain.model.Expense
import org.composemultiplatform.domain.repository.ExpenseRepository

class UpdateExpenseUseCase(
    private val expenseRepository: ExpenseRepository
) {

    operator fun invoke(expense: Expense): Flow<UiState<Unit>> = flow {
        try {
            emit(UiState.Loading())
            if (expense.amount <= 0) {
                emit(UiState.Error("Amount must be greater than 0"))
                return@flow
            }
            if (expense.description.isBlank()) {
                emit(UiState.Error("Description cannot be empty"))
                return@flow
            }
            expenseRepository.updateExpense(expense)
            emit(UiState.Success(Unit))
        }catch (e: Exception) {
            emit(UiState.Error(e.message ?: "Error updating expense"))
        }
    }

}