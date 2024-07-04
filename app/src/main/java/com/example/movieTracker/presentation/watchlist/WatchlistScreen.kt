package com.example.movieTracker.presentation.watchlist

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.movieTracker.presentation.Screen
import com.example.movieTracker.presentation.movie_list.components.MovieListItem
import com.example.movieTracker.ui.components.TopBar

@Composable
fun WatchlistScreen(
    navController: NavController,

    viewModel: WatchlistViewModel = hiltViewModel()
) {
    val state by viewModel.watchlistState.collectAsState()

    val movies by viewModel.getWatchlistMovies().collectAsState(initial = emptyList())

    LaunchedEffect(key1 = Unit) {
        viewModel.getWatchlistMovies().collect {
        }
    }


    Scaffold(topBar = {
        TopBar(title = "Watchlist", showBackButton = false, navController = null)
    }) { innerPadding ->

        Surface(modifier = Modifier.padding(innerPadding)) {

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(movies) { movie ->
                    MovieListItem(movie = movie, onItemClick = {
                        navController.navigate(Screen.MovieDetailScreen.route + "/${movie.id}")
                    })

                }

            }

        }

    }
}