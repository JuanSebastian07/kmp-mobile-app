package org.composemultiplatform.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.composemultiplatform.domain.model.AuthUser
import org.composemultiplatform.domain.repository.AuthRepository

class AuthRepositoryAndroidImpl : AuthRepository {

    private val auth = Firebase.auth

    override val currentUser: Flow<AuthUser?> = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            val user = auth.currentUser?.let {
                AuthUser(
                    uid = it.uid,
                    email = it.email ?: ""
                )
            }
            trySend(user)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose { auth.removeAuthStateListener(authStateListener) }
    }

    override suspend fun signInWithEmail(
        email: String,
        password: String
    ): Result<AuthUser> = withContext(Dispatchers.IO) {
        try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val user = result.user

            if (user != null) {
                val authUser = AuthUser(
                    uid = user.uid,
                    email = user.email ?: ""
                )
                Result.success(authUser)
            } else {
                Result.failure(Exception("User is null"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}