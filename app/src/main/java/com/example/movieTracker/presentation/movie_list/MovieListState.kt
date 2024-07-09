package com.example.movieTracker.presentation.movie_list

import com.example.movieTracker.domain.model.Movie

data class MovieListState(
    val isLoading: Boolean = true,
    val movies: List<Movie> = emptyList(),
    val error: String = "",

    )