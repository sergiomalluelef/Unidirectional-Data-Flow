# Unidireccional data flow - UDF

Este repositorio se dedica a demostrar la lógica del patrón UDF (Unidirectional Data Flow) para la capa de presentación, específicamente gestionada por el ViewModel. En este enfoque, se establece un flujo unidireccional de datos que sigue un proceso organizado.

La lógica comienza con la generación de eventos a través de un SharedFlow. Estos eventos son evaluados por una función de extensión que se encarga de gestionar la lógica de los eventos y transformarlos en nuevos estados.
```
    private fun UiEvent.handlerUserEvent(): Flow<UiState> = when (this) {
        UiEvent.LoadPictures -> getPictures()
    }
```
Estos nuevos estados son entonces establecidos como un nuevo valor para un StateFlow.

```
    init {
        eventHandler.userEvents().buffer()
            .flatMapMerge { event ->
                event.handlerUserEvent()
            }.onEach {
                _state.value = it
            }.launchIn(viewModelScope)
    }
```
El StateFlow, que representa el estado de la aplicación, es monitoreado y evaluado en la capa de interfaz de usuario (UI). Conforme el estado cambia, la interfaz de usuario se adapta en consecuencia, reflejando la información más actualizada y proporcionando una experiencia de usuario coherente y receptiva.

```
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
```

En resumen, este repositorio muestra cómo implementar el patrón UDF para mantener una dirección única y organizada del flujo de datos en la capa de presentación. Esto garantiza que la interfaz de usuario se actualice de manera eficiente en función de los cambios en el estado de la aplicación, lo que mejora la experiencia del usuario.
