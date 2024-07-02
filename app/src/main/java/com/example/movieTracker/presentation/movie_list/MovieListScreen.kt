package com.example.movieTracker.presentation.movie_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.movieTracker.presentation.Screen
import com.example.movieTracker.presentation.movie_list.components.MovieListItem
import com.example.movieTracker.ui.components.TopBar
import com.valentinilk.shimmer.shimmer


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(
    navController: NavController,
    viewModel: MovieListViewModel = hiltViewModel(),

    ) {
    val state by viewModel.state.collectAsState()

    val searchText by viewModel.searchText.collectAsState()
    val filteredMovies by viewModel.movies.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()






    Box(modifier = Modifier.fillMaxSize()) {


        Column(modifier = Modifier) {
            TopBar(title = "Discover Movies", false, null)
            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                value = searchText,
                onValueChange = { newValue ->
                    viewModel.onSearchTextChanged(newValue)
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),

                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "search icon",
                        tint = Color.Gray,
                    )

                },
                trailingIcon = {
                    if (searchText.isNotEmpty()) {
                        IconButton(onClick = { viewModel.onSearchTextChanged("") }) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "clear icon",
                                tint = Color.Gray
                            )

                        }
                    }
                },

                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Unspecified,
                    focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent

                ),

                shape = RoundedCornerShape(6.dp),
                singleLine = true,
                placeholder = { Text(text = "Search") }
            )

            if (isSearching) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3), // Number of columns in the grid
                    contentPadding = PaddingValues(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (state.isLoading) {
                        items(24) {
                            Box(
                                modifier = Modifier
                                    .height(200.dp)
                                    .padding(16.dp)
                                    .clip(shape = RoundedCornerShape(6.dp))
                                    .shimmer()
                                    .background(Color.LightGray)
                            )
                        }
                    } else {
                        items(filteredMovies) { movie ->
                            MovieListItem(
                                movie = movie,
                                onItemClick = {
                                    navController.navigate(Screen.MovieDetailScreen.route + "/${movie.id}")
                                }
                            )
                        }


                        if (filteredMovies.isEmpty()) {
                            items(1) {

                                Column() {
                                    Text(text = "Movei not Found")
                                    Icon(
                                        imageVector = Icons.Filled.Search,
                                        contentDescription = "",
                                        Modifier.size(200.dp)
                                    )
                                }
                            }
                        }


                    }
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

    }
}

