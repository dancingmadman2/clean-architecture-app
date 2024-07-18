package com.example.movieTracker.data.local

import androidx.datastore.core.DataStore
import com.example.movieTracker.di.watchlist_datastore.addToWatchlist
import com.example.movieTracker.di.watchlist_datastore.readData
import com.example.movieTracker.di.watchlist_datastore.removeFromWatchlist
import com.example.movieTracker.di.watchlist_datastore.saveData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DatastoreLocalDataSource @Inject constructor(
    private val dataStore: DataStore<Set<Int>>
) : LocalDataSource {

    /*
    override suspend fun loadWatchlist(): Flow<Set<Int>> {
        return dataStore.loadWatchlist()
    }
     */
    override suspend fun loadWatchlist(): Flow<Set<Int>> {
        return dataStore.readData()
    }

    override suspend fun saveWatchlist(watchlist: Set<Int>) {
        dataStore.saveData(watchlist)
    }

    override suspend fun addToWatchlist(movieId: Int) {
        dataStore.addToWatchlist(movieId)
    }

    override suspend fun removeFromWatchlist(movieId: Int) {
        dataStore.removeFromWatchlist(movieId)
    }

    /*
    override suspend fun saveWatchlist(watchlist: Set<Int>) {
        dataStore.saveWatchlist(watchlist)
    }*/


}