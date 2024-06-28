package com.example.myapplication.presentation.movie_list

import com.example.myapplication.domain.model.Movie

data class MovieListState (
     val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val error: String = ""
)