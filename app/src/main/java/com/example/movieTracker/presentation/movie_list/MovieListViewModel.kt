package com.example.movieTracker.presentation.movie_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieTracker.common.Resource
import com.example.movieTracker.domain.model.Movie
import com.example.movieTracker.domain.usecase.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MovieListState())
    val state: StateFlow<MovieListState> = _state.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching.asStateFlow()

    // private val _movies = MutableStateFlow<List<Movie>>(emptyList())

    val movies: StateFlow<List<Movie>> = searchText
        .debounce(300L)
        .onEach {
            _isSearching.value = true
        }
        .combine(state) { query, state ->
            if (query.isBlank()) {
                state.movies
            } else {
                Log.d(
                    "query-movies",
                    state.movies.filter { it.title.contains(query, ignoreCase = true) }.toString()
                )
                state.movies.filter { it.title.contains(query, ignoreCase = true) }
            }
        }
        .onEach {
            _isSearching.value = false
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _state.value.movies
        )

    init {
        getMovies()
    }

    fun onSearchTextChanged(text: String) {
        Log.d("onsearchtext", " value: $text")
        _searchText.value = text
        Log.d("onsearchtext", "_searchText  ${_searchText.value}")
    }

    private fun getMovies() {
        getMoviesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val movies = result.data ?: emptyList()
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = "",
                            movies = movies
                        )

                    }
//                    _movies.update { movies }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(

                            error = result.message ?: "Unexpected error has occurred.",
                            isLoading = false,
                        )
                    }

                }

                is Resource.Loading -> {
                    _state.update {
                        it.copy(
                            isLoading = true,
                            error = "",
                            movies = emptyList()

                        )
                    }

                }
            }
        }.launchIn(viewModelScope)
    }
}
