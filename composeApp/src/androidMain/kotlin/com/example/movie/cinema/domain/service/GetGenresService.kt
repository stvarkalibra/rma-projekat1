package com.example.movie.cinema.domain.service

import com.example.movie.cinema.model.Genre

class GetGenresService(
    private val moviesRepository: com.example.movie.cinema.domain.repository.MoviesRepository
) {
    suspend operator fun invoke(): List<Genre> {
        return moviesRepository.getGenres()
    }
}