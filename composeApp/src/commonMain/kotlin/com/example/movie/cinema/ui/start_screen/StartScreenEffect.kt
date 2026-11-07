package com.example.movie.cinema.ui.start_screen

sealed class StartScreenEffect {

    data class OpenMovieDetails(
        val targetMovieId: String
    ) : StartScreenEffect()

    data object OpenFilters : StartScreenEffect()
}