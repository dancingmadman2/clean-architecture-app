package com.example.movieTracker.di

import android.content.Context
import androidx.datastore.core.DataStore
import com.example.movieTracker.common.Constants
import com.example.movieTracker.data.local.DatastoreLocalDataSource
import com.example.movieTracker.data.local.LocalDataSource
import com.example.movieTracker.data.remote.MovieApi
import com.example.movieTracker.data.remote.MovieApiRemoteDataSource
import com.example.movieTracker.data.remote.RemoteDataSource
import com.example.movieTracker.data.repository.MovieRepositoryImpl
import com.example.movieTracker.di.watchlist_datastore.watchlistDatastore
import com.example.movieTracker.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder().addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieApi(okHttpClient: OkHttpClient): MovieApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(MovieApi::class.java)
    }


    @Provides
    @Singleton
    fun provideMovieRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource,
        dispatcher: CoroutineDispatcher
    ): MovieRepository {
        return MovieRepositoryImpl(remoteDataSource, localDataSource, dispatcher)
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(
        dataStore: DataStore<Set<Int>>
    ): LocalDataSource {
        return DatastoreLocalDataSource(dataStore)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        api: MovieApi
    ): RemoteDataSource {
        return MovieApiRemoteDataSource(api)
    }


    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context) = context.watchlistDatastore
}
