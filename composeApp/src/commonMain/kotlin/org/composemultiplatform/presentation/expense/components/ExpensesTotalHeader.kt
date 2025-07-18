package org.composemultiplatform.presentation.expense.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.composemultiplatform.presentation.ui.Theme.customColors

@Composable
fun ExpensesTotalHeader(total: Double) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.customColors.colorExpenseItem),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().height(130.dp).padding(16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = "$: $total",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.customColors.colorText
            )
            Text(
                modifier = Modifier.align(Alignment.BottomEnd),
                text = "COP",
                color = MaterialTheme.customColors.colorText
            )
        }
    }
}