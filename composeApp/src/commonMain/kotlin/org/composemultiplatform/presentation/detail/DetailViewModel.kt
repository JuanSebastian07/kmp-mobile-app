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
import org.composemultiplatform.domain.model.Expense
import org.composemultiplatform.domain.model.ExpenseCategory
import org.composemultiplatform.domain.use_case.add_expense.AddExpenseUseCase
import org.composemultiplatform.domain.use_case.get_expense.GetExpenseUseCase
import org.composemultiplatform.domain.use_case.update_expense.UpdateExpenseUseCase

class DetailViewModel(
    private val getExpenseUseCase: GetExpenseUseCase,
    private val addExpenseUseCase: AddExpenseUseCase,
    private val updateExpenseUseCase: UpdateExpenseUseCase,
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

    fun addExpense(amount: Double, description: String, category: ExpenseCategory) {
        val expense = Expense(
            id = _uiState.value.expense?.id ?: 0,
            amount = amount,
            description = description,
            category = category,
        )

        addExpenseUseCase(expense).onEach { result ->
            when (result) {
                is UiState.Loading -> {
                    _uiState.value = _uiState.value.copy(isLoading = true, error = "")
                }

                is UiState.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.message ?: "Error al guardar el gasto"
                    )
                }

                is UiState.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "",
                        isSuccess = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun updateExpense(amount: Double, description: String, category: ExpenseCategory){
        val expense = Expense(
            id = _uiState.value.expense?.id ?: 0,
            amount = amount,
            description = description,
            category = category
        )

        updateExpenseUseCase(expense).onEach { result ->
            when(result){
                is UiState.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.message ?: "Error al actualizar el gasto"
                    )
                }
                is UiState.Loading -> {
                    _uiState.value = _uiState.value.copy(isLoading = true, error = "")
                }
                is UiState.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "",
                        isSuccess = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

}