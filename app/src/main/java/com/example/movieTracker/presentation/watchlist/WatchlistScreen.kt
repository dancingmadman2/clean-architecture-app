package com.example.movieTracker.presentation.watchlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.movieTracker.presentation.Screen
import com.example.movieTracker.presentation.movie_list.components.MovieListItem
import com.example.movieTracker.ui.components.TopBar
import com.valentinilk.shimmer.shimmer

@Composable
fun WatchlistScreen(
    navController: NavController,

    viewModel: WatchlistViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    val movies by viewModel.getWatchlistMovies().collectAsState(initial = emptyList())

    LaunchedEffect(key1 = Unit) {
        viewModel.getWatchlistMovies().collect {
        }
    }


    Scaffold(topBar = {
        TopBar(title = "Watchlist", showBackButton = false, navController = null)
    }) { innerPadding ->

        Surface(modifier = Modifier.padding(innerPadding)) {

            if (state.isLoading) {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    modifier = Modifier.fillMaxSize(),
                ) {
                    items(24) {
                        Box(
                            modifier = Modifier
                                .height(125.dp)
                                .fillMaxWidth()
                                .padding(16.dp)
                                .clip(shape = RoundedCornerShape(6.dp))
                                .shimmer()
                                .background(Color.LightGray)
                        )
                    }
                }
            } else {

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
}