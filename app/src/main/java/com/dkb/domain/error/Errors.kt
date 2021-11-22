package com.dkb.domain.error

import java.net.SocketTimeoutException
import java.net.UnknownHostException


sealed class ErrorType {
    object UnknownError : ErrorType()
    object NoDataFetched : ErrorType()
    object TimeoutError : ErrorType()
}

fun transformException(t: Throwable): ErrorType = when (t) {
    is UnknownHostException -> ErrorType.TimeoutError
    is SocketTimeoutException -> ErrorType.TimeoutError
    else -> ErrorType.UnknownError
}