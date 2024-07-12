package com.example.movieTracker.presentation.watchlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieTracker.common.Resource
import com.example.movieTracker.domain.model.Movie
import com.example.movieTracker.domain.repository.MovieRepository
import com.example.movieTracker.domain.usecase.GetMovieByIdUseCase
import com.example.movieTracker.presentation.watchlist.components.Watchlist
import com.example.movieTracker.presentation.watchlist.components.WatchlistState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    //private val dataStore: DataStore<Set<Int>>,
    private val movieRepository: MovieRepository,
    private val getMovieByIdUseCase: GetMovieByIdUseCase,
) : ViewModel() {
    private val watchlist = Watchlist()
    private val _state = MutableStateFlow(WatchlistState())
    val state: StateFlow<WatchlistState> = _state

    //   private val _watchlistState = MutableStateFlow<Set<Int>>(emptySet())
    // val watchlistState: StateFlow<Set<Int>> = _watchlistState

    init {
        viewModelScope.launch {
            movieRepository.getWatchlist().firstOrNull()?.let { storedSet ->
                //   _watchlistState.value = storedSet
                storedSet.forEach { movieId ->
                    watchlist.addMovie(movieId)
                }
            }
            _state.update { it.copy(watchlistMovieIds = watchlist.getMovieIds()) }
        }
        //loadWatchlist()

    }

    private fun saveWatchlist() {
        viewModelScope.launch {
            movieRepository.saveWatchlist(watchlist.getMovieIds())

        }
    }


    /*
        private fun saveWatchlist() {
            viewModelScope.launch {
                dataStore.updateData {
                    val updatedIds = watchlist.getMovieIds()
                    _watchlistState.value = updatedIds
                    updatedIds
                }

                Log.d("watchlist", "movieIds: ${watchlist.getMovieIds()}")
            }
        }*/

    /*
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

     */

    fun addToWatchlist(movieId: Int) {
        watchlist.addMovie(movieId)
        _state.update {
            it.copy(
                watchlistMovieIds = watchlist.getMovieIds()
            )
        }
        saveWatchlist()

        Log.d("watchlist", "state movie_ids: ${_state.value.watchlistMovieIds}")
    }

    fun removeFromWatchlist(movieId: Int) {
        watchlist.removeMovie(movieId)

        _state.update {
            it.copy(
                watchlistMovieIds = watchlist.getMovieIds()
            )
        }
        saveWatchlist()
        Log.d("watchlist", "state movie_ids: ${_state.value.watchlistMovieIds}")
    }


    fun getWatchlistMovies(): Flow<List<Movie>> = flow {
        val movieIds = watchlist.getMovieIds()
        val movies = movieIds.mapNotNull { movieId ->
            getMovieByIdUseCase(movieId).first { it is Resource.Success }.data
        }
        emit(movies)
        _state.update {
            it.copy(
                isLoading = false,
            )
        }
    }


    /*
    fun getWatchlistMovies(): Flow<List<Movie>> = flow {
        val movieIds = watchlist.getMovieIds()
        emit(movieIds.mapNotNull { movieId ->
            getMovieByIdUseCase(movieId).first { it is Resource.Success }
                .data
        })
    }
     */

    fun getWatchlist(): Set<Int> {
        Log.d("watchlist", "movieIds: ${watchlist.getMovieIds()}")
        return watchlist.getMovieIds()
    }


}
