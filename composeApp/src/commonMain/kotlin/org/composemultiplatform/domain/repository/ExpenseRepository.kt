package org.composemultiplatform.domain.repository

import org.composemultiplatform.domain.model.Expense
import org.composemultiplatform.domain.model.ExpenseCategory

interface ExpenseRepository {
    suspend fun getExpenses(): List<Expense>
    suspend fun getExpenseById(id: Long): Expense
    suspend fun addExpense(expense: Expense)
    suspend fun updateExpense(expense: Expense)
    suspend fun getCategories(): List<ExpenseCategory>
    suspend fun deleteExpense(id: String)
}