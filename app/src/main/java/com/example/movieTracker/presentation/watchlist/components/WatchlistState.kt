package com.example.movieTracker.presentation.watchlist.components

import com.example.movieTracker.domain.model.Movie


data class WatchlistState(
    val isLoading: Boolean = true,
    val movie: Movie? = null,
    val error: String = "",
    val watchlistMovieIds: Set<Int> = emptySet()
)
