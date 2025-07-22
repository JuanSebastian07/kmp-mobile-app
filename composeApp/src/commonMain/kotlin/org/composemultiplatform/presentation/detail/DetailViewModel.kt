package org.composemultiplatform.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import org.composemultiplatform.core.Constants
import kotlinx.coroutines.flow.onEach
import org.composemultiplatform.core.UiState
import org.composemultiplatform.domain.use_case.get_expense.GetExpenseUseCase

class DetailViewModel(
    private val getExpenseUseCase: GetExpenseUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExpenseUiState())
    val uiState: StateFlow<ExpenseUiState> = _uiState.asStateFlow()

    init {
        savedStateHandle.get<Long>(Constants.PARAM_EXPENSE_ID)?.let { expenseId ->
            getExpense(expenseId)
        }
    }

    private fun getExpense(id: Long) {
        getExpenseUseCase(id).onEach { result ->
            when (result) {
                is UiState.Loading -> {
                    _uiState.value = ExpenseUiState(isLoading = true)
                }

                is UiState.Error -> {
                    _uiState.value = ExpenseUiState(error = result.message ?: "Error")
                }

                is UiState.Success -> {
                    _uiState.value = ExpenseUiState(expense = result.data)
                }
            }
        }.launchIn(viewModelScope)
    }

}