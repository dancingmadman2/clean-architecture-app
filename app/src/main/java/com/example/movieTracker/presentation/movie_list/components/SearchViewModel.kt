package com.example.movieTracker.presentation.movie_list.components

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieTracker.domain.model.Movie
import com.example.movieTracker.presentation.movie_list.MovieListViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@OptIn(FlowPreview::class)

class SearchViewModel @Inject constructor(
    private val movieListViewModel: MovieListViewModel
) :
    ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching.asStateFlow()


    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = searchText
        .debounce(300L)
        .onEach {

            _isSearching.value = true
        }
        .combine(_movies) { query, movies ->

            if (query.isBlank()) {
                movies

            } else {
                Log.d(
                    "query-movies",
                    movies.filter { it.title.contains(query, ignoreCase = true) }.toString()
                )
                Log.d("query-movies", "_searchText1: ${_searchText.value}")
                movies.filter { it.title.contains(query, ignoreCase = true) }


            }

        }
        .onEach {
            Log.d("query-movies", "_searchText2: ${_searchText.value}")
            _isSearching.value = false

        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),

            _movies.value

        )

    init {
        viewModelScope.launch {
            _movies.value = movieListViewModel.state.value.movies
        }
    }

    fun onSearchTextChanged(text: String) {
        Log.d("onsearchtext", " value: $text")
        _searchText.value = text
        Log.d("onsearchtext", "_searchText  ${_searchText.value}")
    }
}
