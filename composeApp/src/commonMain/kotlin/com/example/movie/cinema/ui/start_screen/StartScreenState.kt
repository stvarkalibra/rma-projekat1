package com.example.movie.cinema.ui.start_screen

import com.example.movie.app.common.BrowseFilters
import com.example.movie.app.common.SortParametar
import com.example.movie.app.common.UiState
import com.example.movie.cinema.model.Genre
import com.example.movie.cinema.model.Movie

data class StartScreenState(
    val screenState: UiState<List<Movie>> = UiState.Idle,
    val sourceMovies: List<Movie> = emptyList(),
    val visibleMovies: List<Movie> = emptyList(),
    val activeSort: SortParametar = SortParametar.RATING,
    val activeFilters: BrowseFilters = BrowseFilters(),
    val pendingFilters: BrowseFilters = BrowseFilters(),
    val imageHost: String = "",
    val refreshing: Boolean = false,
    val itemsCount: Int = 0,
    val pagesCount: Int = 0,
    val activePage: Int = 1,
    val limitPerPage: Int = 30,
    val genreState: UiState<List<Genre>> = UiState.Idle,
    val genreItems: List<Genre> = emptyList()
)