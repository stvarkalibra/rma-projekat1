package com.example.movie.core.di

import org.koin.dsl.module

val repositoryModule = module {

    single<com.example.movie.cinema.domain.repository.MoviesRepository> {
        _root_ide_package_.com.example.movie.cinema.data.repository.MoviesRepositoryImpl(
            moviesApi = get()
        )
    }

    factory<com.example.movie.cinema.domain.service.GetMoviesService> {
        _root_ide_package_.com.example.movie.cinema.domain.service.GetMoviesService(
            moviesRepository = get()
        )
    }

    factory<com.example.movie.cinema.domain.service.GetMovieDetailsService> {
        _root_ide_package_.com.example.movie.cinema.domain.service.GetMovieDetailsService(
            moviesRepository = get()
        )
    }

    factory<com.example.movie.cinema.domain.service.GetGenresService> {
        _root_ide_package_.com.example.movie.cinema.domain.service.GetGenresService(
            moviesRepository = get()
        )
    }

    factory<com.example.movie.cinema.domain.service.GetImageBaseService> {
        _root_ide_package_.com.example.movie.cinema.domain.service.GetImageBaseService(
            moviesRepository = get()
        )
    }
}