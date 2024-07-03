package com.example.movieTracker.di.watchlist_datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore


val Context.watchlistDatastore: DataStore<Set<Int>> by dataStore(
    fileName = "watchlist.pb",
    serializer = WatchlistSerializer
)