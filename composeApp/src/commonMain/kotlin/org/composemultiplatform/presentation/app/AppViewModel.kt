package org.composemultiplatform.presentation.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.composemultiplatform.domain.use_case.get_current_user.GetCurrentUserUseCase

class AppViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel()  {

    private val _uiState = MutableStateFlow(AppState())
    val uiState: StateFlow<AppState> = _uiState.asStateFlow()

    private val _isSplashReady = MutableStateFlow(false)
    val isSplashReady: StateFlow<Boolean> = _isSplashReady.asStateFlow()

    init {
        observeAuthState()
    }

    private fun observeAuthState() {
        getCurrentUserUseCase().onEach { user ->
            _uiState.value = _uiState.value.copy(
                isAuthenticated = user != null,
                currentUser = user,
                isLoading = false
            )
            _isSplashReady.value = true
        }.launchIn(viewModelScope)
    }
}