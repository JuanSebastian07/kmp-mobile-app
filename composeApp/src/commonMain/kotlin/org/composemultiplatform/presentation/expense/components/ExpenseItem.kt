package org.composemultiplatform.presentation.expense.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.composemultiplatform.domain.model.Expense
import org.composemultiplatform.presentation.ui.Theme.customColors

@Composable
fun ExpenseItem(expense: Expense, onExpenseClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 2.dp)
            .clickable { onExpenseClick() },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.customColors.colorExpenseItem),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.customColors.colorExpenseItem
            ) {
                Image(
                    modifier = Modifier.padding(8.dp),
                    imageVector = expense.icon,
                    colorFilter = ColorFilter.tint(MaterialTheme.customColors.colorText),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
            }

            Column(modifier = Modifier.weight(1f).padding(start = 16.dp)) {
                Text(
                    text = expense.category.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.customColors.colorText,
                )
                Text(
                    text = expense.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.customColors.colorText,
                )
            }

            Text(
                text = "$: ${expense.amount}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.customColors.colorText,
            )
        }

    }

}
