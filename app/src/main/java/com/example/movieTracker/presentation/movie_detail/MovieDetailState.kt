package com.example.movieTracker.presentation.movie_detail

import com.example.movieTracker.domain.model.MovieDetail

data class MovieDetailState (
     val isLoading: Boolean = false,
    val movie: MovieDetail? = null,
    val error: String = ""
)