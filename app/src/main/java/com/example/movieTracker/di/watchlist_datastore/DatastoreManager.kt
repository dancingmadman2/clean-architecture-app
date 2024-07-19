package com.example.movieTracker.di.watchlist_datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(private val dataStore: DataStore<Preferences>) {

    private val gson = Gson()

    suspend fun <T> saveValue(key: String, value: T) {
        val json = gson.toJson(value)
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = json
        }
    }

    fun <T> getValue(key: String, type: Class<T>): Flow<T?> {
        return dataStore.data.map { preferences ->
            val json = preferences[stringPreferencesKey(key)]
            if (json != null) gson.fromJson(json, type) else null
        }
    }
}