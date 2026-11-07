package com.example.movie.core.navigation

object AppRoutes {

    const val MOVIES_ROOT = "movies_root" //Parent graph
    const val MOVIES_LIST = "movies_list" //Pocetni ekran
    const val FILTER_MOVIES = "filter_movies" //Filter ekran
    const val MOVIE_DETAILS = "movie_details" //Details ekran

    const val MOVIE_ID_ARG = "movieId" //Da bi Details znao koji film otvara

    const val MOVIE_DETAILS_ROUTE = "$MOVIE_DETAILS/{$MOVIE_ID_ARG}" //Compozicija Details i ID

    fun movieDetails(targetMovieId: String): String {
        return "$MOVIE_DETAILS/$targetMovieId" //Vraca details rutu za id
    }
}