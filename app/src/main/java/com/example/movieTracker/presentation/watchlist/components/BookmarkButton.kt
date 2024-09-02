package com.example.movieTracker.presentation.watchlist.components

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movieTracker.presentation.watchlist.WatchlistViewModel

@Composable
fun BookmarkButton(
    watchlistViewModel: WatchlistViewModel = hiltViewModel(),
    movieId: Int,
    isOnAppbar: Boolean = false,
) {


    val state by watchlistViewModel.state.collectAsState()
    // val watchlistState by watchlistViewModel.watchlistState.collectAsState()

    val isBookmarkedState = remember(state) {
        mutableStateOf(state.watchlistMovieIds.contains(movieId))
    }
    var isBookmarked by isBookmarkedState


    //Log.d("anan", "anan: ${state.watchlistMovieIds}")
    // var isBookmarked by isBookmarkedState


    //  val isBookmarked = watchlistViewModel.isMovieInWatchlist(movieId)


    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = {


            if (isBookmarked) {
                watchlistViewModel.removeFromWatchlist(movieId)

            } else {

                watchlistViewModel.addToWatchlist(movieId)
            }

            isBookmarked = state.watchlistMovieIds.contains(movieId)
            Log.d("watchlist", "isBookmarked: $isBookmarked")
        }) {
            Icon(
                imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                contentDescription = "",
                tint = if (isOnAppbar) Color.White else Color.Black
            )
        }
    }
}