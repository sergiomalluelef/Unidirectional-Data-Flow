package com.sergidev.feature.unidirectionaldataflow.main.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class MainRepository @Inject constructor() {
    fun getPictures(): Flow<Boolean> = flow {
        delay(5_000L)
        emit(true)
    }
}