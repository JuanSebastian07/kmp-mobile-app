package org.composemultiplatform.presentation

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.composemultiplatform.presentation.detail.DetailScreen
import org.composemultiplatform.presentation.expense.ExpenseScreen
import org.composemultiplatform.presentation.ui.Theme.AppTheme
import org.composemultiplatform.presentation.util.Detail
import org.composemultiplatform.presentation.util.Expense
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    AppTheme {
        NavHost(
            navController = navController,
            startDestination = Expense
        ) {
            composable<Expense> {
                ExpenseScreen(navController = navController)
            }

            composable<Detail> {
                DetailScreen()
            }
        }
    }
}