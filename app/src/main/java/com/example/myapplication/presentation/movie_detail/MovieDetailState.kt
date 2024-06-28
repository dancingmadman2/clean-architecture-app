package com.example.myapplication.presentation.movie_detail

import com.example.myapplication.domain.model.MovieDetail

data class MovieDetailState (
     val isLoading: Boolean = false,
    val movie: MovieDetail? = null,
    val error: String = ""
)