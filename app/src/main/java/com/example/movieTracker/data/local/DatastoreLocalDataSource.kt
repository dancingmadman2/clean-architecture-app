package com.example.movieTracker.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.movieTracker.di.watchlist_datastore.addToPreferences
import com.example.movieTracker.di.watchlist_datastore.loadPreferences
import com.example.movieTracker.di.watchlist_datastore.removeFromPreferences
import javax.inject.Inject

class DatastoreLocalDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : LocalDataSource {


    override suspend fun loadWatchlist(): Set<Int> {
        return dataStore.loadPreferences()
    }

    override suspend fun addToWatchlist(movieId: Int) {
        dataStore.addToPreferences(movieId)
    }

    override suspend fun removeFromWatchlist(movieId: Int) {
        dataStore.removeFromPreferences(movieId)
    }

    override suspend fun saveWatchlist(watchlist: Set<Int>) {
        return dataStore.removeFromPreferences(0)
    }


}