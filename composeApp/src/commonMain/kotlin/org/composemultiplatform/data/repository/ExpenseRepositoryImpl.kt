package org.composemultiplatform.data.repository

import com.expenseApp.database.AppDatabase
import org.composemultiplatform.data.ExpenseManager
import org.composemultiplatform.domain.model.Expense
import org.composemultiplatform.domain.model.ExpenseCategory
import org.composemultiplatform.domain.repository.ExpenseRepository

class ExpenseRepositoryImpl (
    private val expenseManager: ExpenseManager,
    appDatabase: AppDatabase
) : ExpenseRepository  {

    private val queries = appDatabase.expensesDbQueries

    override suspend fun getExpenses(): List<Expense> {
        return queries.selectAll().executeAsList().map {
            Expense(
                id = it.id,
                amount = it.amount,
                description = it.description,
                category = ExpenseCategory.valueOf(it.categoryName)
            )
        }
    }

    override suspend fun addExpense(expense: Expense) {
        queries.transaction {
            queries.insert(
                amount = expense.amount,
                categoryName = expense.category.name,
                description = expense.description
            )
        }
    }

    override suspend fun updateExpense(expense: Expense) {
        queries.transaction {
            queries.update(
                id = expense.id,
                amount = expense.amount,
                categoryName = expense.category.name,
                description = expense.description,
            )
        }
    }

    override suspend fun getCategories(): List<ExpenseCategory> {
        return queries.categories().executeAsList().map {
            ExpenseCategory.valueOf(it)
        }
    }

    override suspend fun deleteExpense(id: String) {
        queries.transaction {
            queries.delete(id.toLong())
        }
    }
}