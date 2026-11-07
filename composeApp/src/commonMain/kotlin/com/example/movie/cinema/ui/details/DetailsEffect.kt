package com.example.movie.cinema.ui.details

sealed class DetailsEffect {

    data object GoBack : DetailsEffect()

    data class LaunchTrailer(
        val trailerUrl: String
    ) : DetailsEffect()
}