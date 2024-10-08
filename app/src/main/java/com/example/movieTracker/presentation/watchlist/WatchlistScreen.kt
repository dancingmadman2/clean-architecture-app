package com.example.movieTracker.presentation.watchlist

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.movieTracker.presentation.Screen
import com.example.movieTracker.presentation.movie_list.components.MovieListItem
import com.example.movieTracker.ui.components.TopBar
import com.valentinilk.shimmer.shimmer

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.WatchlistScreen(
    navController: NavController,
    animatedVisibilityScope: AnimatedVisibilityScope,
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
                        MovieListItem(
                            movie = movie,
                            animatedVisibilityScope = animatedVisibilityScope,
                            onItemClick = {
                                navController.navigate(Screen.MovieDetailScreen.route + "/${movie.id}")
                            },
                        )

                    }

                }
            }
            if (state.watchlistMovieIds.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "",
                        Modifier.size(64.dp)
                    )
                    Text(
                        text = "Watchlist is empty",
                        modifier = Modifier
                            .padding(top = 8.dp),
                        style = TextStyle(
                            fontSize = 20.sp
                        )
                    )

                }
            }

        }

    }
}