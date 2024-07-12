package com.example.movieTracker.data.repository

import com.example.movieTracker.data.local.LocalDataSource
import com.example.movieTracker.data.remote.RemoteDataSource
import com.example.movieTracker.data.remote.dto.movie.MovieDto
import com.example.movieTracker.data.remote.dto.movie_detail.credits.MovieCreditsDto
import com.example.movieTracker.data.remote.dto.movie_detail.overview.MovieDetailDto
import com.example.movieTracker.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    //private val api: MovieApi,
    //private val dataStore: DataStore<Set<Int>>,
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,

    ) : MovieRepository {

    override suspend fun getMovies(): List<MovieDto> {
        return remoteDataSource.getMovies()

    }

    override suspend fun getMovieDetail(movieId: Int): MovieDetailDto {
        return remoteDataSource.getMovieDetail(movieId)
    }

    override suspend fun getMovieCredits(movieId: Int): MovieCreditsDto {
        return remoteDataSource.getMovieCredits(movieId)
    }

    override suspend fun getMovieById(movieId: Int): MovieDto? {
        return getMovies().find { it.id == movieId }
    }

    override suspend fun loadWatchlist(): Flow<Set<Int>> {
        return localDataSource.loadWatchlist()
            .flowOn(Dispatchers.IO)
    }

    override suspend fun addToWatchlist(movieId: Int) = withContext(Dispatchers.IO) {
        localDataSource.addToWatchlist(movieId)
    }

    override suspend fun removeFromWatchlist(movieId: Int) = withContext(Dispatchers.IO) {
        localDataSource.removeFromWatchlist(movieId)
    }

    override suspend fun saveWatchlist(watchlist: Set<Int>) = withContext(Dispatchers.IO) {
        localDataSource.saveWatchlist(watchlist)

    }


}

