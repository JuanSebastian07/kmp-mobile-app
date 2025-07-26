package org.composemultiplatform.presentation.detail

import org.composemultiplatform.domain.model.Expense

data class ExpenseUiState(
    val expense: Expense? = null,
    val error: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false

)