package com.example.movieTracker.presentation.movie_detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieTracker.common.Constants
import com.example.movieTracker.common.Resource
import com.example.movieTracker.domain.usecase.GetMovieDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _state = MutableStateFlow(MovieDetailState())
    val state: StateFlow<MovieDetailState> = _state

    init {
        val movieIdString = savedStateHandle.get<String>(Constants.MOVIE_ID)
        movieIdString?.toIntOrNull()?.let { movieId ->
            Log.d("MOVIE_ID", "Movie Id: $movieId")
            getMovieDetail(movieId)
        }
    }

    /*
        init {
            savedStateHandle.get<Int>(Constants.MOVIE_ID)?.let { movieId ->
                getMovieDetail(movieId)
            }
        }


     */
    private fun getMovieDetail(movieId: Int) {
        getMovieDetailUseCase(movieId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            movie = result.data,
                            isLoading = false,
                            error = ""
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            error = result.message ?: "An unexpected error occured",
                            isLoading = false,
                            movie = null,
                        )
                    }
                }

                is Resource.Loading -> {
                    _state.update {
                        it.copy(
                            error = "",
                            isLoading = true,
                            movie = null,
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}

