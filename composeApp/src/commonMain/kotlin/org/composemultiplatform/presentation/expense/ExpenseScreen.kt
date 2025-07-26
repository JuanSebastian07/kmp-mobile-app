package org.composemultiplatform.presentation.expense

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.composemultiplatform.presentation.expense.components.AllExpensesHeader
import org.composemultiplatform.presentation.expense.components.ExpenseItem
import org.composemultiplatform.presentation.expense.components.ExpensesTotalHeader
import org.composemultiplatform.presentation.ui.Theme.customColors
import org.composemultiplatform.presentation.util.DetailRoute
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ExpenseScreen(
    navController: NavController,
    viewModel: ExpensesViewModel = koinViewModel(),
    snackbarHostState: SnackbarHostState
) {

    val uiState by viewModel.uiState.collectAsState()


    val backStackEntry = navController.currentBackStackEntry
    LaunchedEffect(backStackEntry?.id) {
        viewModel.refreshExpenses()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(DetailRoute())
                },
                shape = CircleShape,
                containerColor = MaterialTheme.customColors.addButtonColor,
                contentColor = MaterialTheme.customColors.addButtonColor,
                modifier = Modifier.size(56.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add expense",
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.customColors.colorTint
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier.padding(innerPadding).fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            stickyHeader {
                Column(
                    modifier = Modifier.background(MaterialTheme.colorScheme.background)
                        .padding(top = 8.dp)
                ) {
                    ExpensesTotalHeader(uiState.total)
                    AllExpensesHeader()
                }
            }
            items(uiState.expenses) { expense ->
                ExpenseItem(
                    expense = expense, onExpenseClick = {
                        navController.navigate(DetailRoute(expenseId = expense.id))//*
                    }
                )
            }
        }
    }
}
