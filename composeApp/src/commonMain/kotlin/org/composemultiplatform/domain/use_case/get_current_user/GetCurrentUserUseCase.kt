package org.composemultiplatform.domain.use_case.get_current_user

import kotlinx.coroutines.flow.Flow
import org.composemultiplatform.domain.model.AuthUser
import org.composemultiplatform.domain.repository.AuthRepository

class GetCurrentUserUseCase(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Flow<AuthUser?> = authRepository.currentUser
}