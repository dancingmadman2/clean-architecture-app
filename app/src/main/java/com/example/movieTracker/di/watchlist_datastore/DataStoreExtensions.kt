package com.example.movieTracker.di.watchlist_datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.movieTracker.data.local.WATCHLIST_KEY
import kotlinx.coroutines.flow.first

suspend fun DataStore<Preferences>.loadPreferences(): Set<Int> {
    return data.first()[WATCHLIST_KEY]?.map {
        it.toInt()
    }?.toSet() ?: emptySet()
}

suspend fun <T> DataStore<Preferences>.saveValue(
    key: Preferences.Key<Set<String>>,
    value: T
) {
    edit { preferences ->
        val currentSet = preferences[key] ?: emptySet()
        preferences[key] = currentSet + value.toString()
    }
}

suspend fun <T> DataStore<Preferences>.removeValue(
    key: Preferences.Key<Set<String>>,
    value: T
) {
    edit { preferences ->
        val currentSet = preferences[key] ?: emptySet()
        preferences[key] = currentSet - value.toString()
    }
}