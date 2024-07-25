package com.example.movieTracker.presentation.movie_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.movieTracker.R
import com.example.movieTracker.presentation.Screen
import com.example.movieTracker.presentation.movie_list.components.MovieListGridItem
import com.example.movieTracker.presentation.movie_list.components.MovieListItem
import com.example.movieTracker.ui.components.TopBar
import com.valentinilk.shimmer.shimmer


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.MovieListScreen(
    navController: NavController,
    viewModel: MovieListViewModel = hiltViewModel(),
    animatedVisibilityScope: AnimatedVisibilityScope,

    ) {

    val state by viewModel.state.collectAsState()

    val searchText by viewModel.searchText.collectAsState()
    val filteredMovies by viewModel.movies.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    var checkedState by remember { mutableStateOf(false) }

    val lazyGridState = rememberLazyGridState()
    val lazyColumnState = rememberLazyListState()


    /*
    LaunchedEffect(lazyGridState) {
        snapshotFlow { lazyGridState.firstVisibleItemScrollOffset }
            .collect { value ->
                showSearchBar = (value < previousOffset && (previousOffset - value) >= 25)
                if (value == 0) showSearchBar = true
                previousOffset = value
            }
    }
    LaunchedEffect(lazyColumnState) {
        snapshotFlow {
            lazyColumnState.firstVisibleItemScrollOffset
        }
            .distinctUntilChanged()
            .collect { value ->
                showSearchBar =
                    (value < previousOffset && (previousOffset - value) >= 25) || value == 0
                previousOffset = value
            }
    }
     */


    val threshold = 25

    val showSearchBar by remember {
        var previousScrollOffset = 0
        derivedStateOf {
            val currentScrollOffset = if (!checkedState) {
                lazyGridState.firstVisibleItemScrollOffset
            } else {
                lazyColumnState.firstVisibleItemScrollOffset
            }

            val scrollingUp = currentScrollOffset < previousScrollOffset
            previousScrollOffset = currentScrollOffset

            when {
                currentScrollOffset <= threshold -> true
                scrollingUp -> true
                !checkedState -> lazyGridState.firstVisibleItemScrollOffset <= threshold
                else -> lazyColumnState.firstVisibleItemScrollOffset <= threshold
            }
        }
    }

    Scaffold(
        topBar = {

            Column() {
                TopBar(
                    title = "Discover Movies",
                    false,
                    collapseAppbar = true,
                    navController = null
                )

            }
        }

    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()


        ) {
            AnimatedVisibility(showSearchBar) {
                TextField(value = searchText,
                    onValueChange = { newValue ->
                        viewModel.onSearchTextChanged(newValue)
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(75.dp)
                        .border(
                            width = 2.dp,
                            brush = SolidColor(MaterialTheme.colorScheme.primary),
                            shape = RoundedCornerShape(6.dp)
                        ),

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
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent

                    ),

                    shape = RoundedCornerShape(6.dp),
                    singleLine = true,
                    placeholder = { Text(text = "Search") })
            }
            Box(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Switch(
                        checked = checkedState,
                        onCheckedChange = { checkedState = it },
                    )
                    Spacer(modifier = Modifier.width(12.5.dp))
                    Text(text = if (checkedState) "List" else "Grid")

                }
            }
            if (isSearching) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {

                if (!checkedState) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        contentPadding = PaddingValues(16.dp),
                        modifier = Modifier.fillMaxSize(),
                        state = lazyGridState
                    ) {
                        if (state.isLoading) {
                            items(12) {
                                Box(
                                    modifier = Modifier
                                        .height(200.dp)
                                        .padding(16.dp)
                                        .clip(shape = RoundedCornerShape(6.dp))
                                        .shimmer()
                                        .background(Color.LightGray)
                                )
                            }
                        } else if (filteredMovies.isEmpty()) {

                            item {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.no_result),
                                        contentDescription = "",
                                        modifier = Modifier.size(200.dp),
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text(text = "Movie not Found", color = Color.Gray)
                                }
                            }

                        } else {
                            items(filteredMovies) { movie ->
                                MovieListGridItem(
                                    movie = movie,
                                    onItemClick = {
                                        navController.navigate(Screen.MovieDetailScreen.route + "/${movie.id}")

                                    },
                                    animatedVisibilityScope = animatedVisibilityScope
                                )

                            }


                        }
                    }
                } else {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        modifier = Modifier
                            .fillMaxSize(),
                        state = lazyColumnState

                    ) {

                        if (state.isLoading) {
                            items(12) {
                                Box(
                                    modifier = Modifier
                                        .height(100.dp)
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                        .clip(shape = RoundedCornerShape(6.dp))
                                        .shimmer()
                                        .background(Color.LightGray)
                                )
                            }
                        } else if (filteredMovies.isEmpty()) {

                            item {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.no_result),
                                        contentDescription = "",
                                        modifier = Modifier.size(200.dp),
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text(text = "Movie not Found", color = Color.Gray)
                                }
                            }

                        } else {
                            items(filteredMovies) { movie ->
                                MovieListItem(
                                    movie = movie,
                                    onItemClick = {
                                        navController.navigate(Screen.MovieDetailScreen.route + "/${movie.id}")
                                    },
                                    animatedVisibilityScope = animatedVisibilityScope
                                )

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

                )
            }
        }

    }

}