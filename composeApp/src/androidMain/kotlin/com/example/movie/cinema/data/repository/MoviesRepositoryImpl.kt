package com.example.movie.cinema.data.repository

import com.example.movie.app.common.BrowseFilters
import com.example.movie.core.network.MoviesApi
import com.example.movie.cinema.data.mapper.toCastDomainList
import com.example.movie.cinema.data.mapper.toDomain
import com.example.movie.cinema.data.mapper.toGenreDomainList
import com.example.movie.cinema.data.dto.ConfigDto
import com.example.movie.cinema.data.dto.ImageItemDto
import com.example.movie.cinema.data.dto.MovieListItemDto
import com.example.movie.cinema.data.dto.VideoDto
import com.example.movie.cinema.model.Genre
import com.example.movie.cinema.model.MovieDetails
import com.example.movie.cinema.model.MoviesPage
import com.example.movie.cinema.domain.repository.MoviesRepository

class MoviesRepositoryImpl(
    private val moviesApi: MoviesApi
) : MoviesRepository {

    private var cachedImageHost: String? = null

    override suspend fun getMovies(
        pageNumber: Int,
        itemsPerPage: Int,
        selectedFilters: BrowseFilters,
        sortField: String,
        sortDirection: String
    ): MoviesPage {
        val pageResponse = moviesApi.getMovies(
            pageNumber = pageNumber,
            itemsPerPage = itemsPerPage,
            searchText = selectedFilters.searchText.takeIf { it.isNotBlank() },
            pickedGenreId = selectedFilters.pickedGenreId,
            yearFrom = selectedFilters.yearFrom,
            yearTo = selectedFilters.yearTo,
            ratingFrom = selectedFilters.ratingFrom,
            sortField = sortField,
            sortDirection = sortDirection
        )

        val movieItems = pageResponse.results.map { movieItemDto: MovieListItemDto ->
            movieItemDto.toDomain()
        }

        return MoviesPage(
            movieItems = movieItems,
            itemsCount = pageResponse.totalCount,
            pagesCount = pageResponse.pageCount,
            activePage = pageResponse.pageNumber,
            limitPerPage = pageResponse.itemsPerPage
        )
    }

    override suspend fun getMovieDetails(targetMovieId: String): MovieDetails {
        val movieDetailsResponse = moviesApi.getMovieDetails(targetMovieId)
        val castResponse = moviesApi.getMovieCast(
            targetMovieId = targetMovieId,
            itemsPerPage = 10
        )
        val imageResponse = moviesApi.getMovieImages(
            targetMovieId = targetMovieId,
            imageType = "backdrop"
        )
        val videoItems = moviesApi.getMovieVideos(
            targetMovieId = targetMovieId,
            videoType = "Trailer"
        )

        val selectedMovie = movieDetailsResponse.toDomain()
        val movieGenres = movieDetailsResponse.genreItems.toGenreDomainList()

        val movieCast = castResponse.results
            .toCastDomainList()
            .take(10)

        val backdropImages = imageResponse.backdropItems
            .mapNotNull { backdropDto: ImageItemDto -> backdropDto.imagePath }
            .distinct()
            .take(3)

        val youtubeTrailerKey = videoItems
            .firstOrNull { videoItem: VideoDto ->
                videoItem.platformName.equals("YouTube", ignoreCase = true) &&
                        videoItem.videoType.equals("Trailer", ignoreCase = true)
            }
            ?.videoKey

        return MovieDetails(
            selectedMovie = selectedMovie,
            movieGenres = movieGenres,
            movieCast = movieCast,
            backdropImages = backdropImages,
            youtubeTrailerKey = youtubeTrailerKey
        )
    }

    override suspend fun getGenres(): List<Genre> {
        return moviesApi.getGenres().toGenreDomainList()
    }

    override suspend fun getImageBaseUrl(): String {
        cachedImageHost?.let { return it }

        val configEntries = moviesApi.getConfig()

        val resolvedImageHost = configEntries
            .firstOrNull { configEntry: ConfigDto ->
                configEntry.configKey == "image_base_url"
            }
            ?.configValue
            ?: "https://image.tmdb.org/t/p/"

        cachedImageHost = resolvedImageHost
        return resolvedImageHost
    }
}