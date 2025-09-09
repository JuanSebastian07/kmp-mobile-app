package org.composemultiplatform.domain.repository

import kotlinx.coroutines.flow.Flow
import org.composemultiplatform.domain.model.AuthUser

interface AuthRepository {
    val currentUser: Flow<AuthUser?>
    suspend fun signInWithEmail(email: String, password: String): Result<AuthUser>
}