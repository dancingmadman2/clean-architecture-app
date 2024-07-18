package com.example.movieTracker.di.watchlist_datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.first

val WATCHLIST_KEY = stringSetPreferencesKey("watchlist")

suspend fun <T> DataStore<T>.saveData(data: T) {
    updateData { data }
}

suspend fun DataStore<Preferences>.loadPreferences(): Set<Int> {
    return data.first()[WATCHLIST_KEY]?.map {
        it.toInt()
    }?.toSet() ?: emptySet()
}


suspend fun DataStore<Preferences>.removeFromPreferences(movieId: Int) {
    edit { preferences ->
        val currentWatchlist = preferences[WATCHLIST_KEY] ?: emptySet()
        preferences[WATCHLIST_KEY] = currentWatchlist - movieId.toString()
    }
}

suspend fun DataStore<Preferences>.addToPreferences(movieId: Int) {
    edit { preferences ->
        val currentWatchlist = preferences[WATCHLIST_KEY] ?: emptySet()
        preferences[WATCHLIST_KEY] = currentWatchlist + movieId.toString()
    }
}
