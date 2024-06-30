package com.example.movieTracker.presentation.movie_credits

import com.example.movieTracker.domain.model.MovieCredits

data class MovieCreditsState(
    val isLoading: Boolean = false,
    val movieCredits: MovieCredits? = null,
    val error: String = ""
)