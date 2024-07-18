package com.example.movieTracker.di.watchlist_datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DataStoreModule {

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

}