package org.composemultiplatform.presentation.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.composemultiplatform.domain.model.ExpenseCategory
import org.composemultiplatform.presentation.detail.components.CategoryBottomSheetContent
import org.composemultiplatform.presentation.detail.components.DetailContent
import org.composemultiplatform.presentation.ui.Theme.customColors
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
private fun LoadingContent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorContent(
    error: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Error",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = error,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    viewModel: DetailViewModel = koinViewModel(),
    onNavigateBack: () -> Unit = {}
) {
    val colors = MaterialTheme.customColors
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()
    val isEditMode = uiState.expense != null
    var amountText by remember { mutableStateOf(0.0) }
    var descriptionText by remember { mutableStateOf("") }
    var categorySelected by remember {
        mutableStateOf(ExpenseCategory.OTHER)
    }

    LaunchedEffect(uiState.expense) {
        if (isEditMode && uiState.expense != null) {
            amountText = uiState.expense!!.amount
            descriptionText = uiState.expense!!.description
            categorySelected = uiState.expense!!.category
        }
    }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )

    LaunchedEffect(sheetState.currentValue) {
        if (sheetState.targetValue == SheetValue.Expanded) {
            keyboardController?.hide()
        }
    }

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onNavigateBack()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = colors.colorTint,
    ) { innerPadding ->
        when {
            uiState.isLoading -> {
                LoadingContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }

            uiState.error.isNotEmpty() -> {
                ErrorContent(
                    error = uiState.error,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }

            else -> {
                DetailContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                    isEditMode = isEditMode,
                    expense = uiState.expense,
                    keyboardController = keyboardController,
                    categorySelected = categorySelected.toString(),
                    onCategoryClick = {
                        scope.launch { sheetState.show() }
                    },
                    onSaveClick = {
                        if (isEditMode){
                            viewModel.updateExpense(amountText, descriptionText, categorySelected)

                        }else{
                            viewModel.addExpense(amountText, descriptionText, categorySelected)
                        }
                    },
                    onAmountChange = {
                        amountText = it
                    },
                    onDescriptionChange = {
                        descriptionText = it
                    }
                )
            }
        }
    }

    CategoryBottomSheet(
        sheetState = sheetState,
        isVisible = sheetState.isVisible,
        onCategorySelected = { category ->
            categorySelected = category
            scope.launch { sheetState.hide() }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategoryBottomSheet(
    sheetState: SheetState,
    isVisible: Boolean,
    onCategorySelected: (ExpenseCategory) -> Unit
) {
    if (isVisible) {
        ModalBottomSheet(
            onDismissRequest = { },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.background,
        ) {
            CategoryBottomSheetContent(
                categoryOptions = ExpenseCategory.entries,
                onCategorySelected = onCategorySelected
            )
        }
    }
}



