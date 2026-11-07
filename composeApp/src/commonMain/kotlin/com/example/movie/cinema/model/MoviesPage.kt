package com.example.movie.cinema.model

data class MoviesPage(
    val movieItems: List<Movie>,
    val itemsCount: Int,
    val pagesCount: Int,
    val activePage: Int,
    val limitPerPage: Int
)