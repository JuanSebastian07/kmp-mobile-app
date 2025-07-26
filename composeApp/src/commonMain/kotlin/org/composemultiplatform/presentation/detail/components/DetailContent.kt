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
    onSaveClick: () -> Unit,
    onAmountChange: (Double) -> Unit = {},
    onDescriptionChange: (String) -> Unit = {},
    isEditMode: Boolean
) {

    val text = if (isEditMode) "Update Expense" else "Save Expense"
    val currentAmount = expense?.amount ?: 0.0
    val currentDescription = expense?.description.orEmpty()

    Column(modifier = modifier) {
        ExpenseAmount(
            keyboardController = keyboardController,
            amount = currentAmount
        ){
            onAmountChange(it)
        }

        Spacer(modifier = Modifier.height(16.dp))

        ExpenseTypeSelector(
            categorySelected = categorySelected,
            onCategorySelected = onCategoryClick
        )

        Spacer(modifier = Modifier.height(16.dp))

        ExpenseDescription(
            keyboardController = keyboardController,
            description = currentDescription
        ){
            onDescriptionChange(it)
        }

        Spacer(modifier = Modifier.height(16.dp))

        ExpenseButton(
            text = text,
            onClick = onSaveClick
        )
    }
}