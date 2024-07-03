package com.example.movieTracker.presentation.to_watch

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieTracker.domain.model.Movie
import com.example.movieTracker.domain.repository.MovieRepository
import com.example.movieTracker.presentation.to_watch.components.Watchlist
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(

    private val movieRepository: MovieRepository,
    private val dataStore: DataStore<Set<Int>>
) :
    ViewModel() {
    private val watchlist = Watchlist()
    private val _watchlistState = MutableStateFlow<Set<Int>>(emptySet())
    val watchlistState: StateFlow<Set<Int>> = _watchlistState


    init {
        loadWatchlist()
    }


    private fun saveWatchlist() {
        viewModelScope.launch {
            dataStore.updateData {
                val updatedSet = watchlist.getMovieIds()
                _watchlistState.value = updatedSet
                updatedSet
            }
        }
    }

    private fun loadWatchlist() {
        viewModelScope.launch {
            dataStore.data.firstOrNull()?.let { storedSet ->
                _watchlistState.value = storedSet
                storedSet.forEach { movieId ->
                    watchlist.addMovie(movieId)
                }
            }
        }
    }


    fun addToWatchlist(movieId: Int) {
        watchlist.addMovie(movieId)
        saveWatchlist()
        Log.d("watchlist", movieId.toString())
    }

    fun removeFromWatchlist(movieId: Int) {
        watchlist.removeMovie(movieId)
        saveWatchlist()
    }

    /*
        fun isMovieInWatchlist(movieId: Int): Boolean {
            return watchlist.contains(movieId)
        }

     */
    fun isMovieInWatchlist(movieId: Int): Boolean {
        return _watchlistState.value.contains(movieId)
    }

    private fun getWatchlistMovies(): Flow<List<Movie>> = flow {
        val movieIds = watchlist.getMovieIds()
        val movies = movieIds.mapNotNull { movieId ->
            movieRepository.getMovieById(movieId)
        }

        emit(movies)
    }


    fun getWatchlist(): Set<Int> {
        Log.d("watchlist", "movieIds: ${watchlist.getMovieIds()}")
        return watchlist.getMovieIds()
    }

    fun logWatchlistMovies() {
        viewModelScope.launch {
            getWatchlistMovies().collect { movies ->
                Log.d("watchlist", "Movies in watchlist: $movies")
            }
        }
    }

}

