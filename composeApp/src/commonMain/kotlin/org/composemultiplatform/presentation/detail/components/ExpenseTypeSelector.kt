package org.composemultiplatform.presentation.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.composemultiplatform.presentation.ui.Theme.customColors

@Composable
fun ExpenseTypeSelector(
    categorySelected: String = "",
    onCategorySelected: () -> Unit,
) {
    val colors = MaterialTheme.customColors

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "Category", color = Color.Gray, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            Text(
                text = categorySelected,
                color = colors.colorText,
                fontWeight = FontWeight.SemiBold
            )
        }
        IconButton(
            modifier = Modifier.clip(RoundedCornerShape(35)).background(colors.colorArrowRound),
            onClick = { onCategorySelected() }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "ArrowDropDown",
                tint = Color.White
            )
        }
    }

    HorizontalDivider(color = Color.Black, thickness = 1.dp, modifier = Modifier.padding(top = 16.dp))
}