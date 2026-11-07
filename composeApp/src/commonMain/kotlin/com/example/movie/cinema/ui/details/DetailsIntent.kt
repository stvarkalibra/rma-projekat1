package com.example.movie.cinema.ui.details

sealed class DetailsIntent {

    data class FetchDetails(
        val targetMovieId: String
    ) : DetailsIntent()

    data object RetryLoad : DetailsIntent()

    data object OnBackClick : DetailsIntent()

    data class OnTrailerClick(
        val trailerKey: String
    ) : DetailsIntent()
}