package com.dkb.presentation.ui.base

import androidx.lifecycle.ViewModel
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import com.dkb.domain.usecase.Error
import com.dkb.domain.usecase.Result
import com.dkb.domain.usecase.Success
import org.koin.core.component.KoinComponent


abstract class BaseViewModel : ViewModel(), CoroutineScope, KoinComponent {

    private val supervisorJob = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + supervisorJob + CoroutineExceptionHandler { _, _ -> }

    val errorLiveEvent: LiveEvent<Error<*>> = LiveEvent()

    fun launchUnit(action: suspend () -> Unit) {
        launch { action() }
    }

    protected inline fun <T> Result<T>.doOnSuccess(action: (T) -> Unit) {
        when (this) {
            is Error -> errorLiveEvent.postValue(this)
            is Success -> action(this.value)
        }
    }

    override fun onCleared() {
        supervisorJob.cancelChildren()
        super.onCleared()
    }
}