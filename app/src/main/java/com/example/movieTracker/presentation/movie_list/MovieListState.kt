package com.example.movieTracker.presentation.movie_list

import com.example.movieTracker.domain.model.Movie

data class MovieListState (
     val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val error: String = ""
)