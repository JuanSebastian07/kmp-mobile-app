package org.composemultiplatform.presentation.app

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.composemultiplatform.presentation.detail.DetailScreen
import org.composemultiplatform.presentation.expense.ExpenseScreen
import org.composemultiplatform.presentation.sign_in.LoginScreen
import org.composemultiplatform.presentation.ui.Theme.AppTheme
import org.composemultiplatform.presentation.util.DetailRoute
import org.composemultiplatform.presentation.util.ExpenseRoute
import org.composemultiplatform.presentation.util.SignInRoute
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    val appViewModel: AppViewModel = koinViewModel()
    val appState by appViewModel.uiState.collectAsState()
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }

    AppTheme {
        NavHost(
            navController = navController,
            startDestination = SignInRoute
        ) {
            composable<SignInRoute> {

                LaunchedEffect(appState.isAuthenticated) {
                    if (appState.isAuthenticated) {
                        navController.navigate(ExpenseRoute) {
                            popUpTo(SignInRoute) { inclusive = true }
                        }
                    }
                }

                if (!appState.isAuthenticated) {
                    LoginScreen(navController = navController)
                }
            }

            composable<ExpenseRoute> {
                ExpenseScreen(
                    navController = navController,
                    snackbarHostState = snackbarHostState
                )
            }

            composable<DetailRoute> { entry ->
                /*val id = entry.toRoute<DetailRoute>().expenseId*/
                DetailScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}