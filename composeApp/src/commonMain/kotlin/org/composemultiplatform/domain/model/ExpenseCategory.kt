package org.composemultiplatform.domain.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CarRental
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.House
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material.icons.filled.Train
import androidx.compose.material.icons.filled.ViewCozy
import androidx.compose.ui.graphics.vector.ImageVector

enum class ExpenseCategory(
    val icon: ImageVector
) {
    GROCERIES(Icons.Default.ShoppingBasket),
    TRANSPORTATION(Icons.Default.Train),
    ENTERTAINMENT(Icons.Default.Movie),
    SNACK(Icons.Default.Fastfood),
    COFFEE(Icons.Default.Coffee),
    CAR(Icons.Default.CarRental),
    OTHER(Icons.Default.ViewCozy),
    HOUSE(Icons.Default.House),
    CLOTHES(Icons.Default.Checkroom)
}