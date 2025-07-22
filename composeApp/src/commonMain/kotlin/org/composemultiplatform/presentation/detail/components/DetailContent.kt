package org.composemultiplatform.presentation.detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.unit.dp
import org.composemultiplatform.domain.model.Expense

@Composable
fun DetailContent(
    modifier: Modifier = Modifier,
    expense: Expense?,
    keyboardController: SoftwareKeyboardController?,
    categorySelected: String,
    onCategoryClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    Column(modifier = modifier) {
        ExpenseAmount(
            keyboardController = keyboardController,
            amount = expense?.amount ?: 0.0
        )

        Spacer(modifier = Modifier.height(16.dp))

        ExpenseTypeSelector(
            categorySelected = expense?.category?.name ?: categorySelected,
            onCategorySelected = onCategoryClick
        )

        Spacer(modifier = Modifier.height(16.dp))

        ExpenseDescription(
            keyboardController = keyboardController,
            description = expense?.description ?: ""
        )

        Spacer(modifier = Modifier.height(16.dp))

        ExpenseButton(
            onClick = onSaveClick
        )
    }
}