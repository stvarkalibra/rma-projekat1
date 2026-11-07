package com.example.movie.cinema.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie.app.common.UiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val getMovieDetailsService: com.example.movie.cinema.domain.service.GetMovieDetailsService,
    private val getImageBaseService: com.example.movie.cinema.domain.service.GetImageBaseService
) : ViewModel() {

    private val _state = MutableStateFlow(DetailsState())
    val state: StateFlow<DetailsState> = _state

    private val _effect = Channel<DetailsEffect>()
    val effect = _effect.receiveAsFlow()

    private var selectedMovieId: String? = null

    fun onIntent(intent: DetailsIntent) {
        when (intent) {
            is DetailsIntent.FetchDetails -> {
                selectedMovieId = intent.targetMovieId
                fetchMovieDetails(intent.targetMovieId)
            }

            DetailsIntent.RetryLoad -> {
                selectedMovieId?.let { movieId ->
                    fetchMovieDetails(movieId)
                }
            }

            DetailsIntent.OnBackClick -> {
                sendBackEffect()
            }

            is DetailsIntent.OnTrailerClick -> {
                sendTrailerEffect(intent.trailerKey)
            }
        }
    }

    private fun fetchMovieDetails(targetMovieId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                screenState = UiState.Loading
            )

            try {
                val resolvedImageHost = getImageBaseService()
                val detailsBundle = getMovieDetailsService(targetMovieId)

                _state.value = _state.value.copy(
                    screenState = UiState.Success(detailsBundle),
                    bundle = detailsBundle,
                    imageHost = resolvedImageHost
                )
            } catch (exception: Exception) {
                _state.value = _state.value.copy(
                    screenState = UiState.Error(
                        description = exception.message ?: "Failed to load movie details"
                    )
                )
            }
        }
    }

    private fun sendBackEffect() {
        viewModelScope.launch {
            _effect.send(DetailsEffect.GoBack)
        }
    }

    private fun sendTrailerEffect(trailerKey: String) {
        viewModelScope.launch {
            _effect.send(
                DetailsEffect.LaunchTrailer(
                    trailerUrl = "https://www.youtube.com/watch?v=$trailerKey"
                )
            )
        }
    }
}