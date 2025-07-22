package org.composemultiplatform.presentation

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.composemultiplatform.presentation.detail.DetailScreen
import org.composemultiplatform.presentation.expense.ExpenseScreen
import org.composemultiplatform.presentation.ui.Theme.AppTheme
import org.composemultiplatform.presentation.util.DetailRoute
import org.composemultiplatform.presentation.util.ExpenseRoute
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    AppTheme {
        NavHost(
            navController = navController,
            startDestination = ExpenseRoute
        ) {
            composable<ExpenseRoute> {
                ExpenseScreen(navController = navController)
            }

            composable<DetailRoute> { entry ->
                /*val id = entry.toRoute<DetailRoute>().expenseId*/
                DetailScreen()
            }
        }
    }
}