package org.composemultiplatform.presentation.expense

import org.composemultiplatform.domain.model.Expense

data class ExpensesUiState(
    val error: String? = "",
    val isLoading: Boolean = false,
    val expenses: List<Expense> = emptyList(),
    val total: Double = 0.0
)