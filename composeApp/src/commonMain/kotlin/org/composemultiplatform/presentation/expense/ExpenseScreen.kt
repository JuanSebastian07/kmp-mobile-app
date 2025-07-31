package org.composemultiplatform.presentation.expense

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.composemultiplatform.domain.model.Expense
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

            if (uiState.expenses.isEmpty() && !uiState.isLoading) {
                item {
                    Text(
                        text = "No tienes gastos aún",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }else {
                items(
                    uiState.expenses,
                    key = { expense -> expense.id }
                ) { expense ->

                    SwipeToActionItem(
                        expense = expense,
                        onExpenseClick = {
                            navController.navigate(
                                DetailRoute(expenseId = expense.id)//*
                            )
                        },
                        onDelete = {
                            viewModel.deleteExpense(expense)
                        },
                        onEdit = { expenseToEdit ->
                            navController.navigate(DetailRoute(expenseId = expenseToEdit.id))
                        }
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToActionItem(
    expense: Expense,
    onExpenseClick: () -> Unit,
    onDelete: (Expense) -> Unit,
    onEdit: (Expense) -> Unit
) {
    val dismissBoxState = rememberSwipeToDismissBoxState(
        confirmValueChange = { dismissValue ->
            when (dismissValue) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    onDelete(expense)
                    true
                }

                SwipeToDismissBoxValue.EndToStart -> {
                    onEdit(expense)
                    false
                }

                SwipeToDismissBoxValue.Settled -> false
            }
        }
    )

    SwipeToDismissBox(
        state = dismissBoxState,
        enableDismissFromStartToEnd = true,
        enableDismissFromEndToStart = true,
        backgroundContent = {
            SwipeBackground(
                swipeDirection = dismissBoxState.dismissDirection
            )
        }
    ) {
        ExpenseItem(
            expense = expense,
            onExpenseClick = onExpenseClick
        )
    }
}

@Composable
fun SwipeBackground(
    swipeDirection: SwipeToDismissBoxValue
) {
    val color = when (swipeDirection) {
        SwipeToDismissBoxValue.StartToEnd -> Color.Red.copy(alpha = 0.8f)
        SwipeToDismissBoxValue.EndToStart -> Color.Green.copy(alpha = 0.8f)
        SwipeToDismissBoxValue.Settled -> MaterialTheme.colorScheme.surface
    }

    val alignment = when (swipeDirection) {
        SwipeToDismissBoxValue.StartToEnd -> Alignment.CenterStart
        SwipeToDismissBoxValue.EndToStart -> Alignment.CenterEnd
        SwipeToDismissBoxValue.Settled -> Alignment.Center
    }

    val icon = when (swipeDirection) {
        SwipeToDismissBoxValue.StartToEnd -> Icons.Default.Delete
        SwipeToDismissBoxValue.EndToStart -> Icons.Default.Edit
        SwipeToDismissBoxValue.Settled -> Icons.Default.Done
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(horizontal = 20.dp),
        contentAlignment = alignment
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White
        )
    }
}

