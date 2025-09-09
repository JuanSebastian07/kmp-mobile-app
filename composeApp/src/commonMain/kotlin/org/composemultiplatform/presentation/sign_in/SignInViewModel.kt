package org.composemultiplatform.presentation.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.composemultiplatform.core.UiState
import org.composemultiplatform.domain.use_case.signIn_with_email.SignInWithEmailUseCase

class SignInViewModel(
    private val signInWithEmailUseCase: SignInWithEmailUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignInState())
    val uiState: StateFlow<SignInState> = _uiState.asStateFlow()

    fun signIn(email: String, password: String) {
        _uiState.value = _uiState.value.copy(
            signInError = null
        )

        signInWithEmailUseCase(email, password).onEach { result ->
            when (result) {
                is UiState.Loading -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = true
                    )
                }

                is UiState.Error -> {
                    _uiState.value = _uiState.value.copy(
                        signInError = result.message,
                        isLoading = false,
                        isSignInSuccessful = false
                    )
                }

                is UiState.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isSignInSuccessful = true,
                        authUser = result.data,
                        isLoading = false,
                        signInError = null
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(signInError = null)
    }

}