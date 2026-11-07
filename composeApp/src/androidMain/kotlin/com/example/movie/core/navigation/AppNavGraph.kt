package com.example.movie.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.movie.cinema.ui.filters.FilterScreen
import com.example.movie.cinema.ui.details.DetailsViewModel
import com.example.movie.cinema.ui.start_screen.StartScreenEffect
import com.example.movie.cinema.ui.start_screen.StartScreenIntent
import com.example.movie.cinema.ui.start_screen.StartScreenViewModel
import com.example.movie.cinema.ui.details.DetailScreen
import com.example.movie.cinema.ui.start_screen.StartScreen
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppRoutes.MOVIES_ROOT
    ) {
        navigation(
            startDestination = AppRoutes.MOVIES_LIST,
            route = AppRoutes.MOVIES_ROOT
        ) {
            composable(route = AppRoutes.MOVIES_LIST) { navBackStackEntry ->
                val rootEntry = remember(navBackStackEntry) {
                    navController.getBackStackEntry(AppRoutes.MOVIES_ROOT)
                }

                val sharedMoviesViewModel: StartScreenViewModel = koinViewModel(
                    viewModelStoreOwner = rootEntry
                )
                val screenState by sharedMoviesViewModel.state.collectAsState()

                LaunchedEffect(sharedMoviesViewModel) {
                    sharedMoviesViewModel.effect.collectLatest { screenEffect ->
                        when (screenEffect) {
                            is StartScreenEffect.OpenMovieDetails -> {
                                navController.navigate(
                                    AppRoutes.movieDetails(screenEffect.targetMovieId)
                                )
                            }

                            StartScreenEffect.OpenFilters -> {
                                navController.navigate(AppRoutes.FILTER_MOVIES)
                            }
                        }
                    }
                }

                StartScreen(
                    state = screenState,
                    onIntent = sharedMoviesViewModel::onIntent,
                    onMovieClick = { selectedMovieId ->
                        sharedMoviesViewModel.onIntent(
                            StartScreenIntent.OpenMovie(selectedMovieId)
                        )
                    },
                    onFilterClick = {
                        sharedMoviesViewModel.onIntent(StartScreenIntent.OpenFilterScreen)
                    }
                )
            }

            composable(route = AppRoutes.FILTER_MOVIES) { navBackStackEntry ->
                val rootEntry = remember(navBackStackEntry) {
                    navController.getBackStackEntry(AppRoutes.MOVIES_ROOT)
                }

                val sharedMoviesViewModel: StartScreenViewModel = koinViewModel(
                    viewModelStoreOwner = rootEntry
                )
                val screenState by sharedMoviesViewModel.state.collectAsState()

                FilterScreen(
                    state = screenState,
                    onIntent = sharedMoviesViewModel::onIntent,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }

        composable(route = AppRoutes.MOVIE_DETAILS_ROUTE) { navBackStackEntry ->
            val selectedMovieId = navBackStackEntry.arguments
                ?.getString(AppRoutes.MOVIE_ID_ARG)
                ?: return@composable

            val detailsViewModel: DetailsViewModel = koinViewModel()
            val detailsScreenState by detailsViewModel.state.collectAsState()

            DetailScreen(
                movieId = selectedMovieId,
                state = detailsScreenState,
                effect = detailsViewModel.effect,
                onIntent = detailsViewModel::onIntent,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}