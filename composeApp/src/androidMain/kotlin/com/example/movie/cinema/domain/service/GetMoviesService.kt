package com.example.movie.cinema.domain.service

import com.example.movie.cinema.domain.repository.MoviesRepository
import com.example.movie.cinema.model.MoviesPage
import com.example.movie.app.common.BrowseFilters

class GetMoviesService(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(
        pageNumber: Int,
        itemsPerPage: Int,
        selectedFilters: BrowseFilters,
        sortField: String,
        sortDirection: String
    ): MoviesPage {
        return moviesRepository.getMovies(
            pageNumber = pageNumber,
            itemsPerPage = itemsPerPage,
            selectedFilters = selectedFilters,
            sortField = sortField,
            sortDirection = sortDirection
        )
    }
}