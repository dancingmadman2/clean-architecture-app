package com.example.movieTracker.presentation.movie_credits

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieTracker.common.Constants
import com.example.movieTracker.common.Resource
import com.example.movieTracker.domain.usecase.GetMovieCreditsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class MovieCreditsViewModel @Inject constructor(
    private val getMovieCreditsUseCase: GetMovieCreditsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _state = mutableStateOf(MovieCreditsState())
    val state: State<MovieCreditsState> = _state

    init {
        val movieIdString = savedStateHandle.get<String>(Constants.MOVIE_ID)
        movieIdString?.toIntOrNull()?.let { movieId ->
            getMovieCredits(movieId)
        }
    }

    private fun getMovieCredits(movieId: Int) {
        getMovieCreditsUseCase(movieId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = MovieCreditsState(movieCredits = result.data)
                }

                is Resource.Error -> {
                    _state.value = MovieCreditsState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }

                is Resource.Loading -> {
                    _state.value = MovieCreditsState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}

