package com.blank.composerestaurant.utils

import com.blank.composerestaurant.ui.common.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

fun <T : Any> flowState(service: suspend () -> T): Flow<UiState<T>> = flow {
    emit(UiState.Loading)
    try {
        emit(UiState.Success(service.invoke()))
    } catch (e: Exception) {
        emit(UiState.Error(e.message.toString()))
    }
}.flowIo()

fun flowStateBoolean(service: suspend () -> Any): Flow<Boolean> = flow {
    try {
        service.invoke()
        emit(true)
    } catch (e: Exception) {
        emit(false)
    }
}.flowIo()

fun <T> Flow<T>.flowIo(): Flow<T> = flowOn(Dispatchers.IO)

