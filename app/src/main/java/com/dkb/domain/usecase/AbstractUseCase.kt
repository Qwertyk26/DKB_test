package com.dkb.domain.usecase

import android.util.Log
import com.dkb.domain.error.ErrorType
import com.dkb.domain.error.transformException
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class AbstractUseCase : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + SupervisorJob() + CoroutineExceptionHandler { _, _ -> Unit }

    protected suspend fun <T> execute(
        handleException: (Throwable) -> ErrorType = { t: Throwable -> transformException(t) },
        action: suspend () -> T?
    ): Result<T> = try {
        withContext(coroutineContext) {
            return@withContext action()
                ?.let { Success(it) }
                ?: Error<T>(ErrorType.NoDataFetched)
        }
    } catch (e: Throwable) {
        Log.e("Error", e.message ?: "")
        Error(handleException(e))
    }
}

sealed class Result<Response>
class Success<Response>(val value: Response) : Result<Response>()
class Error<Response>(val type: ErrorType) : Result<Response>()

fun <T> Result<T>.getOrNull(): T? = when (this) {
    is Success -> this.value
    else -> null
}