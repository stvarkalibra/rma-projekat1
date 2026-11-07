package com.example.movie.cinema.domain.service

class GetImageBaseService(
    private val moviesRepository: com.example.movie.cinema.domain.repository.MoviesRepository
) {
    suspend operator fun invoke(): String {
        return moviesRepository.getImageBaseUrl()
    }
}