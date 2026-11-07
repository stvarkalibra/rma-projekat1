package com.example.movie.cinema.ui.details

import com.example.movie.app.common.UiState
import com.example.movie.cinema.model.MovieDetails

data class DetailsState(
    val screenState: UiState<MovieDetails> = UiState.Idle,
    val bundle: MovieDetails? = null,
    val imageHost: String = ""
)