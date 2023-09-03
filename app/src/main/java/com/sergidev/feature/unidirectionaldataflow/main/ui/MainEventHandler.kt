package com.sergidev.feature.unidirectionaldataflow.main.ui

import com.sergidev.feature.unidirectionaldataflow.main.presentation.UiEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class MainEventHandler @Inject constructor() {
    private val userEvents = MutableSharedFlow<UiEvent>()
    var coroutineScope: CoroutineScope? = null

    fun userEvents(): Flow<UiEvent> = userEvents.asSharedFlow()

    fun initialEventIntent() {
        coroutineScope?.launch {
            userEvents.emit(UiEvent.LoadPictures)
        }
    }
}