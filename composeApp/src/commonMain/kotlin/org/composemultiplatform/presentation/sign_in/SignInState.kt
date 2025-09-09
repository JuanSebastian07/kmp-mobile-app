package org.composemultiplatform.presentation.sign_in

import org.composemultiplatform.domain.model.AuthUser

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null,
    val isLoading: Boolean = false,
    val authUser: AuthUser? = null
)