package com.example.movieTracker.presentation.to_watch.components


class Watchlist {
    private val movieIds = mutableSetOf<Int>()

    fun addMovie(movieId: Int) {
        movieIds.add(movieId)
    }

    fun removeMovie(movieId: Int) {
        movieIds.remove(movieId)
    }

    fun contains(movieId: Int): Boolean {
        return movieIds.contains(movieId)
    }

    fun getMovieIds(): Set<Int> {
        return movieIds
    }
}
