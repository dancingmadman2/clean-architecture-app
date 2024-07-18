package com.example.movieTracker.di.watchlist_datastore

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow


fun DataStore<Set<Int>>.loadWatchlist(): Flow<Set<Int>> {
    return data
}

suspend fun DataStore<Set<Int>>.saveWatchlist(watchlist: Set<Int>) {
    updateData {
        watchlist
    }
}

suspend fun DataStore<Set<Int>>.removeFromWatchlist(movieId: Int) {
    updateData { currentSet ->
        currentSet - movieId
    }
}

suspend fun DataStore<Set<Int>>.addToWatchlist(movieId: Int) {
    updateData { currentSet ->
        currentSet + movieId
    }
}

suspend fun <T> DataStore<T>.saveData(data: T) {
    updateData { data }
}

fun <T> DataStore<T>.readData(): Flow<T> {
    return data
}

