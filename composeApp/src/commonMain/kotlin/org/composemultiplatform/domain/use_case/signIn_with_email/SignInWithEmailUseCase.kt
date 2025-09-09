package org.composemultiplatform.domain.use_case.signIn_with_email

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.composemultiplatform.core.UiState
import org.composemultiplatform.domain.model.AuthUser
import org.composemultiplatform.domain.repository.AuthRepository

class SignInWithEmailUseCase(
    private val authRepository: AuthRepository
) {
    operator fun invoke(username: String, password: String): Flow<UiState<AuthUser>> =
        flow {
            emit(UiState.Loading())

            val result = authRepository.signInWithEmail(username, password)

            if (result.isSuccess) {
                val authUser = result.getOrNull()!!
                emit(UiState.Success(authUser))
            } else {
                val exception = result.exceptionOrNull()!!
                emit(UiState.Error(exception.message ?: "Error"))
            }
        }
}