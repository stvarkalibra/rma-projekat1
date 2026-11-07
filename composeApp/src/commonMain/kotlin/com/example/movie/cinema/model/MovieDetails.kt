package com.example.movie.cinema.model

data class MovieDetails(
    val selectedMovie: Movie,
    val movieGenres: List<Genre>,
    val movieCast: List<CastMember>,
    val backdropImages: List<String>,
    val youtubeTrailerKey: String?
)