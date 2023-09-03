package com.sergidev.feature.unidirectionaldataflow.main.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sergidev.feature.unidirectionaldataflow.main.presentation.MainViewModel
import com.sergidev.feature.unidirectionaldataflow.main.presentation.UiState
import com.sergidev.feature.unidirectionaldataflow.main.ui.component.DefaultComponent
import com.sergidev.feature.unidirectionaldataflow.main.ui.component.ErrorComponent
import com.sergidev.feature.unidirectionaldataflow.main.ui.component.LoadingComponent
import com.sergidev.feature.unidirectionaldataflow.main.ui.component.SuccessfulComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    Scaffold {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            MainContent(
                state = state,
                event = viewModel.event
            )
        }
    }
}

@Composable
internal fun MainContent(
    state: UiState,
    event: MainEventHandler,
) {
    when (state) {
        UiState.Default -> DefaultComponent {
            event.initialEventIntent()
        }

        UiState.InProgress -> LoadingComponent()

        UiState.Success -> SuccessfulComponent {
            event.initialEventIntent()
        }

        UiState.Error -> ErrorComponent()
    }
}

