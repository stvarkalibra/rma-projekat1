package com.example.movie.cinema.data.mapper

import com.example.movie.cinema.data.dto.GenreDto
import com.example.movie.cinema.model.Genre

fun GenreDto.toDomain(): Genre {
    return Genre(
        genreId = genreId,
        genreName = genreLabel
    )
}

fun List<GenreDto>.toGenreDomainList(): List<Genre> {
    return map { genreDto: GenreDto ->
        genreDto.toDomain()
    }
}