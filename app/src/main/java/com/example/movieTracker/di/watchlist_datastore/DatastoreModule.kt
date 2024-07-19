package com.example.movieTracker.di.watchlist_datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton


private const val PREFERENCES = "watchlist_preferences"

@InstallIn(SingletonComponent::class)
@Module
object DataStoreModule {
    /*
        @Singleton
        @Provides
        fun provideProtoDataStore(
            @ApplicationContext appContext: Context
        ): DataStore<Set<Int>> {
            return DataStoreFactory.create(
                serializer = WatchlistSerializer,
                produceFile = { appContext.dataStoreFile("watchlist.pb") },
                corruptionHandler = null,
                scope = CoroutineScope(Dispatchers.IO)
            )
        }
     */
    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(SharedPreferencesMigration(appContext, PREFERENCES)),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { appContext.preferencesDataStoreFile(PREFERENCES) }
        )
    }
}