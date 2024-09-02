package com.example.movieTracker.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.example.movieTracker.di.watchlist_datastore.loadPreferences
import com.example.movieTracker.di.watchlist_datastore.removeValue
import com.example.movieTracker.di.watchlist_datastore.saveValue
import javax.inject.Inject

val WATCHLIST_KEY = stringSetPreferencesKey("watchlist")

class DatastoreLocalDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : LocalDataSource {


    override suspend fun loadWatchlist(): Set<Int> {
        return dataStore.loadPreferences()
    }

    override suspend fun addToWatchlist(movieId: Int) {
        dataStore.saveValue(WATCHLIST_KEY, movieId)
    }

    override suspend fun removeFromWatchlist(movieId: Int) {
        dataStore.removeValue(WATCHLIST_KEY, movieId)
    }

    override suspend fun saveBool(test: Boolean) {
        TODO("Not yet implemented")
    }

    // depreciated
    override suspend fun saveWatchlist(watchlist: Set<Int>) {
        dataStore.saveValue(WATCHLIST_KEY, 0)
    }


}