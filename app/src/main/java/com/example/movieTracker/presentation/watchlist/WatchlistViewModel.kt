package com.example.movieTracker.presentation.watchlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieTracker.common.Resource
import com.example.movieTracker.domain.model.Movie
import com.example.movieTracker.domain.usecase.AddToWatchlistUseCase
import com.example.movieTracker.domain.usecase.GetMovieByIdUseCase
import com.example.movieTracker.domain.usecase.LoadWatchlistUseCase
import com.example.movieTracker.domain.usecase.RemoveFromWatchlistUseCase
import com.example.movieTracker.domain.usecase.SaveWatchlistUseCase
import com.example.movieTracker.presentation.watchlist.components.Watchlist
import com.example.movieTracker.presentation.watchlist.components.WatchlistState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val loadWatchlistUseCase: LoadWatchlistUseCase,
    private val saveWatchlistUseCase: SaveWatchlistUseCase,
    private val addToWatchlistUseCase: AddToWatchlistUseCase,
    private val removeFromWatchlistUseCase: RemoveFromWatchlistUseCase,
    private val getMovieByIdUseCase: GetMovieByIdUseCase,

    ) : ViewModel() {
    private val watchlist = Watchlist()
    private val _state = MutableStateFlow(WatchlistState())
    val state: StateFlow<WatchlistState> = _state

    init {

        viewModelScope.launch {
            loadWatchlistUseCase().let { storedSet ->
                //   _watchlistState.value = storedSet
                storedSet.forEach { movieId ->
                    watchlist.addMovie(movieId)
                }
            }
            _state.update { it.copy(watchlistMovieIds = watchlist.getMovieIds()) }
        }


    }

    private fun saveWatchlist() {
        viewModelScope.launch {
            saveWatchlistUseCase(watchlist.getMovieIds())
            //movieRepository.saveWatchlist(watchlist.getMovieIds())

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
        viewModelScope.launch {
            addToWatchlistUseCase(movieId)
        }
        //saveWatchlist()
        //addToWatchlist(movieId)

        Log.d("watchlist", "state movie_ids: ${_state.value.watchlistMovieIds}")
    }

    fun removeFromWatchlist(movieId: Int) {
        watchlist.removeMovie(movieId)

        _state.update {
            it.copy(
                watchlistMovieIds = watchlist.getMovieIds()
            )
        }
        viewModelScope.launch {
            removeFromWatchlistUseCase(movieId)
        }
        //saveWatchlist()
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
