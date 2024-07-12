package com.example.movieTracker.data.local

import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun getWatchlist(): Flow<Set<Int>>
    suspend fun saveWatchlist(watchlist: Set<Int>)
    suspend fun addToWatchlist(movieId: Int)
    suspend fun removeFromWatchlist(movieId: Int)
}