package com.example.movie.cinema.data.mapper

import com.example.movie.cinema.data.dto.GenreDto
import com.example.movie.cinema.data.dto.MovieDetailsDto
import com.example.movie.cinema.data.dto.MovieListItemDto
import com.example.movie.cinema.model.Movie

fun MovieListItemDto.toDomain(): Movie {
    return Movie(
        movieId = imdbId,
        movieTitle = movieTitle,
        movieOverview = "",
        posterUrl = posterImagePath,
        backdropUrl = null,
        releaseDateYear = releaseYear,
        imdbScore = imdbScore ?: 0.0,
        imdbVotesCount = imdbVotesCount ?: 0,
        trendingScore = popularityScore ?: 0.0,
        categoryIds = genreItems.map { genreDto: GenreDto -> genreDto.genreId },
        durationMinutes = null,
        languageCode = null,
        productionBudget = null,
        boxOfficeRevenue = null,
        tmdbScore = null
    )
}

fun MovieDetailsDto.toDomain(): Movie {
    return Movie(
        movieId = imdbId,
        movieTitle = movieTitle,
        movieOverview = movieOverview.orEmpty(),
        posterUrl = posterImagePath,
        backdropUrl = backdropImagePath,
        releaseDateYear = releaseYear,
        imdbScore = imdbScore ?: 0.0,
        imdbVotesCount = imdbVotesCount ?: 0,
        trendingScore = popularityScore ?: 0.0,
        categoryIds = genreItems.map { genreDto: GenreDto -> genreDto.genreId },
        durationMinutes = runtimeMinutes,
        languageCode = originalLanguageCode,
        productionBudget = budgetAmount,
        boxOfficeRevenue = revenueAmount,
        tmdbScore = tmdbScore
    )
}

fun List<MovieListItemDto>.toMovieDomainList(): List<Movie> {
    return map { movieDto: MovieListItemDto ->
        movieDto.toDomain()
    }
}