package com.example.movie.cinema.ui.start_screen

import com.example.movie.app.common.BrowseFilters
import com.example.movie.app.common.SortParametar

sealed class StartScreenIntent {

    data object FetchMovies : StartScreenIntent()

    data object RetryLoad : StartScreenIntent()

    data class ChangeSort(
        val selectedSort: SortParametar
    ) : StartScreenIntent()

    data class SubmitFilters(
        val selectedFilters: BrowseFilters
    ) : StartScreenIntent()

    data class OpenMovie(
        val targetMovieId: String
    ) : StartScreenIntent()

    data object OpenFilterScreen : StartScreenIntent()

    data object FilterScreenOpened : StartScreenIntent()

    data class DraftQueryChanged(
        val typedText: String
    ) : StartScreenIntent()

    data class DraftGenreSelected(
        val pickedGenreId: Int?
    ) : StartScreenIntent()

    data class DraftMinYearChanged(
        val yearFrom: Int?
    ) : StartScreenIntent()

    data class DraftMaxYearChanged(
        val yearTo: Int?
    ) : StartScreenIntent()

    data class DraftMinRatingChanged(
        val ratingFrom: Double?
    ) : StartScreenIntent()

    data object DraftClearAll : StartScreenIntent()

    data object ApplyDraftFilters : StartScreenIntent()

    data object LoadNextPage : StartScreenIntent()

    data object LoadPreviousPage : StartScreenIntent()
}