package org.composemultiplatform.domain.model

data class Expense(
    val id: Long,
    val amount: Double,
    val description: String,
    val category: ExpenseCategory,
){
    val icon = category.icon
}


