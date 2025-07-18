package org.composemultiplatform.presentation.ui.Theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Esquema de colores oscuro
private val DarkColorScheme = darkColorScheme(
    primary = Purple,
    onSurface = DarkText,
    surface = DarkBackground,
    onBackground = DarkText,
    background = DarkBackground,
    onPrimary = Color.White,
    secondary = DarkExpenseItem,
    tertiary = DarkText,
    // You can add more colors if needed.
    primaryContainer = Color(0xFF3700B3),
    onPrimaryContainer = Color.White
)

// Esquema de colores claro
private val LightColorScheme = lightColorScheme(
    primary = Purple,
    onSurface = LightText,
    surface = LightBackground,
    onBackground = LightText,
    background = LightBackground,
    onPrimary = Color.White,
    secondary = LightExpenseItem,
    tertiary = LightText,
    // You can add more colors if needed.
    primaryContainer = Color(0xFF6200EE),
    onPrimaryContainer = Color.White
)

// Colores personalizados adicionales que no están en MaterialTheme
data class CustomColors(
    val colorExpenseItem: Color,
    val addButtonColor: Color,
    val colorArrowRound: Color,
    val colorText: Color,
    val colorTint: Color
)

// Función para obtener colores personalizados
fun getCustomColors(isDarkMode: Boolean): CustomColors {
    return if (isDarkMode) {
        CustomColors(
            colorExpenseItem = DarkExpenseItem,
            addButtonColor = LightBackground,
            colorArrowRound = DarkText,
            colorText = DarkText,
            colorTint = Color.Black
        )
    } else {
        CustomColors(
            colorExpenseItem = LightExpenseItem,
            addButtonColor = DarkBackground,
            colorArrowRound = LightText,
            colorText = LightText,
            colorTint = Color.White
        )
    }
}

// CompositionLocal para colores personalizados
val LocalCustomColors = compositionLocalOf { getCustomColors(false) }

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    val customColors = getCustomColors(darkTheme)

    CompositionLocalProvider(LocalCustomColors provides customColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            shapes = MaterialTheme.shapes.copy(
                small = AbsoluteCutCornerShape(0.dp),
                medium = AbsoluteCutCornerShape(0.dp),
                large = AbsoluteCutCornerShape(0.dp)
            ),
            content = content
        )
    }
}

// Extension for easy access to custom colors
val MaterialTheme.customColors: CustomColors
    @Composable
    get() = LocalCustomColors.current