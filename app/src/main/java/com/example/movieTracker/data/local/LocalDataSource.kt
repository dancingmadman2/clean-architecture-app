package com.example.movieTracker.data.local

interface LocalDataSource {
    suspend fun loadWatchlist(): Set<Int>
    suspend fun saveWatchlist(watchlist: Set<Int>)
    suspend fun addToWatchlist(movieId: Int)
    suspend fun removeFromWatchlist(movieId: Int)
    suspend fun saveBool(test: Boolean)
}