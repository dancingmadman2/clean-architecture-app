package com.example.movieTracker.data.local

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DatastoreLocalDataSource @Inject constructor(
    private val dataStore: DataStore<Set<Int>>
) : LocalDataSource {


    override suspend fun getWatchlist(): Flow<Set<Int>> {
        return dataStore.data
    }

    override suspend fun addToWatchlist(movieId: Int) {
        dataStore.updateData { currentSet ->
            currentSet + movieId
        }
    }

    override suspend fun removeFromWatchlist(movieId: Int) {
        dataStore.updateData { currentSet ->
            currentSet - movieId
        }
    }

    override suspend fun saveWatchlist(watchlist: Set<Int>) {
        dataStore.updateData {
            watchlist
        }

    }

}