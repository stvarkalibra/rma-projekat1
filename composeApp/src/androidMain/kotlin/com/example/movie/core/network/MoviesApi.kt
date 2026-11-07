package com.example.movie.core.network

import com.example.movie.cinema.data.dto.ConfigDto
import com.example.movie.cinema.data.dto.GenreDto
import com.example.movie.cinema.data.dto.ImagesDto
import com.example.movie.cinema.data.dto.MovieDetailsDto
import com.example.movie.cinema.data.dto.MovieListItemDto
import com.example.movie.cinema.data.dto.PaginatedResponseDto
import com.example.movie.cinema.data.dto.PersonDto
import com.example.movie.cinema.data.dto.VideoDto
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query

interface MoviesApi {

    @GET("movies")
    suspend fun getMovies(
        @Query("page") pageNumber: Int,
        @Query("page_size") itemsPerPage: Int,
        @Query("query") searchText: String?,
        @Query("genre_id") pickedGenreId: Int?,
        @Query("min_year") yearFrom: Int?,
        @Query("max_year") yearTo: Int?,
        @Query("min_rating") ratingFrom: Double?,
        @Query("sort_by") sortField: String,
        @Query("sort_order") sortDirection: String
    ): PaginatedResponseDto<MovieListItemDto>

    @GET("movies/{id}")
    suspend fun getMovieDetails(
        @Path("id") targetMovieId: String
    ): MovieDetailsDto

    @GET("movies/{id}/cast")
    suspend fun getMovieCast(
        @Path("id") targetMovieId: String,
        @Query("page_size") itemsPerPage: Int = 10
    ): PaginatedResponseDto<PersonDto>

    @GET("movies/{id}/images")
    suspend fun getMovieImages(
        @Path("id") targetMovieId: String,
        @Query("type") imageType: String = "backdrop"
    ): ImagesDto

    @GET("movies/{id}/videos")
    suspend fun getMovieVideos(
        @Path("id") targetMovieId: String,
        @Query("type") videoType: String = "Trailer"
    ): List<VideoDto>

    @GET("genres")
    suspend fun getGenres(): List<GenreDto>

    @GET("config")
    suspend fun getConfig(): List<ConfigDto>
}