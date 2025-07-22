package org.composemultiplatform.presentation.util

import kotlinx.serialization.Serializable

@Serializable
data object ExpenseRoute

@Serializable
data class DetailRoute(val expenseId: Long? = null)