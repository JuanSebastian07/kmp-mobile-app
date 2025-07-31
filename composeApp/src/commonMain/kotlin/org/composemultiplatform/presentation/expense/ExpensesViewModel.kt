package org.composemultiplatform.presentation.expense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.launchIn
import org.composemultiplatform.core.UiState
import org.composemultiplatform.domain.model.Expense
import org.composemultiplatform.domain.use_case.delete_expense.DeleteExpenseUseCase
import org.composemultiplatform.domain.use_case.get_expenses.GetExpensesUseCase

class ExpensesViewModel(
    private val getExpensesUseCase: GetExpensesUseCase,
    private val deleteExpenseUseCase: DeleteExpenseUseCase,
) : ViewModel()  {

    private val _uiState = MutableStateFlow(ExpensesUiState())
    val uiState: StateFlow<ExpensesUiState> = _uiState.asStateFlow()

    init {
        getExpenses()
    }

    fun refreshExpenses() {
        getExpenses()
    }

    private fun getExpenses() {
        getExpensesUseCase().onEach { result ->
            when(result) {
                is UiState.Loading -> {
                    _uiState.value = ExpensesUiState(isLoading = true)
                }
                is UiState.Error -> {
                    _uiState.value = ExpensesUiState(error = result.message ?: "Error")
                }
                is UiState.Success -> {
                    _uiState.value = ExpensesUiState(
                        expenses = result.data ?: emptyList(),
                        total = result.data?.sumOf { it.amount } ?: 0.0
                    )
                }
            }

        }.launchIn(viewModelScope)
    }

    fun deleteExpense(expense: Expense) {
        deleteExpenseUseCase(expense.id.toString()).onEach { result ->
            when(result) {
                is UiState.Loading -> {
                    _uiState.value = _uiState.value.copy(isLoading = true)
                }
                is UiState.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.message ?: "Error al eliminar"
                    )
                }
                is UiState.Success -> {
                    val updatedExpenses = _uiState.value.expenses.filter { it.id != expense.id }
                    _uiState.value = _uiState.value.copy(
                        expenses = updatedExpenses,
                        total = updatedExpenses.sumOf { it.amount },
                        isLoading = false,
                        error = null
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}