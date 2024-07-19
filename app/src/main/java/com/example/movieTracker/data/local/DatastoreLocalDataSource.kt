package com.example.movieTracker.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.example.movieTracker.di.watchlist_datastore.loadPreferences
import com.example.movieTracker.di.watchlist_datastore.removeFromPreferences
import com.example.movieTracker.di.watchlist_datastore.removeValue
import com.example.movieTracker.di.watchlist_datastore.saveValue
import javax.inject.Inject

val WATCHLIST_KEY = stringSetPreferencesKey("watchlist")
val OTHER_KEY = stringSetPreferencesKey("watchlist2")

class DatastoreLocalDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : LocalDataSource {


    override suspend fun loadWatchlist(): Set<Int> {
        return dataStore.loadPreferences()
    }

    /*
        override suspend fun addToWatchlist(movieId: Int) {
            dataStore.addToPreferences(movieId)
        }

        override suspend fun removeFromWatchlist(movieId: Int) {
            dataStore.removeFromPreferences(movieId)
        }

     */
    override suspend fun addToWatchlist(movieId: Int) {
        dataStore.saveValue(WATCHLIST_KEY, movieId)
    }
    /*
        override suspend fun saveBool(test: Boolean) {
            dataStore.saveValue(OTHER_KEY, true,
                onError = { exception ->
                    Log.d("datastore", "error: $exception")
                },
                onSuccess = {
                    Log.d("datastore", "success")
                }
            )
        }*/

    override suspend fun saveBool(test: Boolean) {
        TODO("Not yet implemented")
    }


    override suspend fun removeFromWatchlist(movieId: Int) {
        dataStore.removeValue(WATCHLIST_KEY, movieId)
    }


    override suspend fun saveWatchlist(watchlist: Set<Int>) {
        dataStore.removeFromPreferences(0)
    }


}