package com.sergidev.feature.unidirectionaldataflow.main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergidev.feature.unidirectionaldataflow.main.data.MainRepository
import com.sergidev.feature.unidirectionaldataflow.main.ui.MainEventHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    private val eventHandler: MainEventHandler
) : ViewModel() {
    private val _state = MutableStateFlow<UiState>(UiState.Default)
    val state = _state.asStateFlow()

    init {
        eventHandler.coroutineScope = viewModelScope
        eventHandler.userEvents().buffer()
            .flatMapMerge { event ->
                event.handlerUserEvent()
            }.onEach {
                _state.value = it
            }.launchIn(viewModelScope)
    }

    private fun UiEvent.handlerUserEvent(): Flow<UiState> = when (this) {
        UiEvent.LoadPictures -> getPictures()
    }

    private fun getPictures(): Flow<UiState> = repository.getPictures()
        .map {
            UiState.Success as UiState
        }.onStart {
            emit(UiState.InProgress)
        }.catch {
            emit(UiState.Error)
        }.flowOn(Dispatchers.IO)

    val event: MainEventHandler
        get() = this.eventHandler
}