package com.sergidev.feature.unidirectionaldataflow.main.presentation

sealed class UiState {
    object Default : UiState()
    object InProgress : UiState()
    object Error : UiState()
    object Success : UiState()
}