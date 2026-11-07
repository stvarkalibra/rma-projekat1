package com.example.movie.app.common

sealed interface UiState<out T> {

    data object Idle : UiState<Nothing>

    data object Loading : UiState<Nothing>

    data class Success<T>(
        val payload: T
    ) : UiState<T>

    data class Error(
        val description: String
    ) : UiState<Nothing>

    data object Empty : UiState<Nothing>
}