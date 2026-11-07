package com.example.movie.core.di

import com.example.movie.cinema.ui.details.DetailsViewModel
import com.example.movie.cinema.ui.start_screen.StartScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        _root_ide_package_.com.example.movie.cinema.ui.start_screen.StartScreenViewModel(
            getMoviesService = get(),
            getImageBaseService = get(),
            getGenresService = get()
        )
    }

    viewModel {
        _root_ide_package_.com.example.movie.cinema.ui.details.DetailsViewModel(
            getMovieDetailsService = get(),
            getImageBaseService = get()
        )
    }
}