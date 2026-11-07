package com.example.movie.cinema.domain.service

import com.example.movie.cinema.model.MovieDetails

class GetMovieDetailsService(
    private val moviesRepository: com.example.movie.cinema.domain.repository.MoviesRepository
) {
    suspend operator fun invoke(targetMovieId: String): MovieDetails {
        return moviesRepository.getMovieDetails(targetMovieId)
    }
}