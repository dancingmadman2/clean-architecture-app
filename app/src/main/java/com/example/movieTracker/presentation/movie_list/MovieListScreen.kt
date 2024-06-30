package com.example.movieTracker.presentation.movie_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.movieTracker.presentation.Screen
import com.example.movieTracker.presentation.movie_list.components.MovieListItem
import com.example.movieTracker.ui.components.TopBar


@Composable
fun MovieListScreen(
    navController: NavController,
    viewModel: MovieListViewModel = hiltViewModel(),
) {
    val state = viewModel.state.value


    Box(modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier) {
            TopBar(title = "Discover Movies", false, null)
            Spacer(modifier = Modifier.height(20.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(3), // Number of columns in the grid
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {

                items(state.movies.size) { index ->
                    val movie = state.movies[index]

                    MovieListItem(
                        movie = movie,
                        onItemClick = {
                            navController.navigate(Screen.MovieDetailScreen.route + "/${movie.id}")
                        }
                    )
                }
            }
        }

        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

