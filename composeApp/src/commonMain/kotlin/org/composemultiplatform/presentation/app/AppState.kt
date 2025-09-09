package org.composemultiplatform.presentation.app

import org.composemultiplatform.domain.model.AuthUser

data class AppState(
    val isAuthenticated: Boolean = false,
    val currentUser: AuthUser? = null,
    val isLoading: Boolean = true
)