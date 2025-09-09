package org.composemultiplatform.data.repository

import cocoapods.FirebaseAuth.FIRAuth
import cocoapods.FirebaseAuth.FIRAuthDataResult
import cocoapods.FirebaseAuth.FIRAuthStateDidChangeListenerHandle
import cocoapods.FirebaseAuth.FIRUser
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import org.composemultiplatform.domain.model.AuthUser
import org.composemultiplatform.domain.repository.AuthRepository
import platform.Foundation.NSError
import kotlin.coroutines.resume

@OptIn(ExperimentalForeignApi::class)
class AuthRepositoryIosImpl : AuthRepository {

    private val auth = FIRAuth.auth()
    private val _state = MutableStateFlow<AuthUser?>(null)
    private var handle: FIRAuthStateDidChangeListenerHandle? = null

    init {
        handle = auth.addAuthStateDidChangeListener { _, user: FIRUser? ->
            if (user != null) {
                _state.value = AuthUser(
                    uid = user.uid(),
                    email = user.email()
                )
            } else {
                _state.value = null
            }
        }
    }

    override val currentUser: Flow<AuthUser?> = _state.asStateFlow()

    override suspend fun signInWithEmail(
        email: String,
        password: String
    ): Result<AuthUser> = withContext(Dispatchers.Main) {
        suspendCancellableCoroutine<Result<AuthUser>> { cont: CancellableContinuation<Result<AuthUser>> ->
            auth.signInWithEmail(
                email = email, password = password
            ) { result: FIRAuthDataResult?, error: NSError? ->
                if(!cont.isActive) return@signInWithEmail
                    when{
                        error != null -> {
                            cont.resume(Result.failure(Exception(error.localizedDescription)))
                        }
                        result?.user() == null -> {
                            cont.resume(Result.failure(Exception("Error")))
                        }
                        else -> {
                            val user = result.user()
                            cont.resume(Result.success(AuthUser(uid = user.uid(), email = user.email() ?: "")))
                        }
                    }
            }
        }
    }

}