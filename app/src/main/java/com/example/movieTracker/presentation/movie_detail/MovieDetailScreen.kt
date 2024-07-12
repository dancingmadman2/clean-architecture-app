package com.example.movieTracker.presentation.movie_detail

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Timelapse
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movieTracker.presentation.movie_credits.MovieCreditsViewModel
import com.example.movieTracker.presentation.movie_detail.components.MovieDetailShimmer
import com.example.movieTracker.presentation.movie_detail.components.RatingBar
import com.example.movieTracker.presentation.watchlist.WatchlistViewModel
import com.example.movieTracker.ui.components.TopBar


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.MovieDetailScreen(
    navController: NavController,
    viewModel: MovieDetailViewModel = hiltViewModel(),
    creditsViewModel: MovieCreditsViewModel = hiltViewModel(),
    animatedVisibilityScope: AnimatedVisibilityScope
) {

    val state by viewModel.state.collectAsState()


    val movie = state.movie
    val credits = creditsViewModel.state.value.movieCredits
    Scaffold(
        topBar = {

            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (movie != null) {
                    TopBar(
                        title = movie.title,
                        navController = navController,
                        showBookmark = true,
                        movieId = movie.id
                    )
                }

                //movie?.let { BookmarkedButton(movieId = it.id) }

            }
        }

    ) { innerPadding ->
        Surface(

            modifier = Modifier
                .fillMaxSize()
        ) {


            Column(
                modifier = Modifier
                    .verticalScroll(
                        rememberScrollState()

                    )
                    .padding(innerPadding)
            ) {

                Spacer(modifier = Modifier.height(20.dp))

                if (state.isLoading) {
                    MovieDetailShimmer()
                } else {
                    Row(Modifier.padding(16.dp)) {
                        Box(
                            modifier = Modifier
                                .clip(shape = RoundedCornerShape(6.dp))
                                .weight(0.8f)
                        ) {
                            val imageUrl =
                                "https://image.tmdb.org/t/p/w185${movie?.posterPath}"
                            AsyncImage(
                                model = imageUrl,
                                contentDescription = "movie poster",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(0.665f)
                                    .sharedElement(
                                        state = rememberSharedContentState(key = "poster/${movie?.id}"),
                                        animatedVisibilityScope = animatedVisibilityScope,
                                        boundsTransform = { _, _ ->

                                            tween(durationMillis = 1000)
                                        }

                                    )
                            )
                        }
                        Spacer(modifier = Modifier.width(24.dp))
                        Column(modifier = Modifier.weight(0.8f)) {
                            Text(
                                text = "${movie?.title}",
                                style = TextStyle(
                                    fontSize = 24.sp,
                                ),
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .sharedElement(
                                        state = rememberSharedContentState(key = "title/${movie?.id}"),
                                        animatedVisibilityScope = animatedVisibilityScope,
                                        boundsTransform = { _, _ ->

                                            tween(durationMillis = 1000)
                                        }

                                    )
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Details(
                                "Release Date: ",
                                "${movie?.releaseDate}",
                                Icons.Filled.DateRange
                            )
                            Details("Genre: ", "${movie?.genres?.get(0)?.name}", Icons.Filled.Info)
                            Details("Runtime : ", "${movie?.runtime} min.", Icons.Filled.Timelapse)

                            Details(
                                label = "Production: ",
                                value = "${movie?.productionCompanies?.get(0)?.name}",
                                icon = Icons.Filled.Movie
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            if (movie != null) {
                                RatingBar(
                                    rating = (movie.voteAverage.div(2).toFloat()),

                                    )
                            }


                        }
                    }

                    Row(

                    ) {
                        if (movie != null) {
                            Details(
                                label = "Budget: ",
                                value = "${formatLargeNumber(movie.budget.toLong())} $",
                                icon = Icons.Filled.AttachMoney
                            )
                        }
                        if (movie != null) {
                            Details(
                                label = "Revenue: ",
                                value = "${formatLargeNumber(movie.revenue.toLong())} $",
                                icon = Icons.Filled.AttachMoney
                            )
                        }
                        if (movie != null) {
                            Details(
                                label = "Popularity: ",
                                value = "${formatLargeNumber(movie.popularity.toLong())} $",
                                icon = Icons.Filled.People
                            )
                        }
                    }

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = "Overview",

                            style = TextStyle(
                                fontWeight = FontWeight.Bold, fontSize = 24.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${movie?.overview}",
                            overflow = TextOverflow.Ellipsis,
                            style = TextStyle(
                                fontSize = 15.sp
                            ),
                            modifier = Modifier.sharedElement(
                                state = rememberSharedContentState(key = "overview/${movie?.id}"),
                                animatedVisibilityScope = animatedVisibilityScope,
                                boundsTransform = { _, _ ->

                                    tween(durationMillis = 1000)
                                }

                            )
                        )
                    }


                    Box(modifier = Modifier.padding(start = 16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = "Director",
                                    style = TextStyle(
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                )

                                Spacer(modifier = Modifier.height(12.dp))
                                Row {
                                    Column {

                                        Box(modifier = Modifier.clip(shape = RoundedCornerShape(6.dp))) {
                                            if (credits?.crew?.get(0)?.profilePath != null) {
                                                val imageUrl =
                                                    "https://image.tmdb.org/t/p/w185${credits.crew[0].profilePath}"
                                                AsyncImage(
                                                    model = imageUrl,
                                                    contentDescription = "director",
                                                    modifier = Modifier
                                                        .scale(1.15F)
                                                        .height(150.dp)
                                                )
                                            } else {
                                                Box(
                                                    modifier = Modifier.clip(
                                                        shape = RoundedCornerShape(
                                                            6.dp
                                                        )
                                                    )
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Filled.Person,
                                                        contentDescription = "",
                                                        modifier = Modifier
                                                            .height(150.dp)
                                                            .scale(1.5F)
                                                            .aspectRatio(0.665F)
                                                    )
                                                }
                                            }

                                        }

                                        Text(text = "${credits?.crew?.get(0)?.name}")
                                    }

                                }

                            }

                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    Box(modifier = Modifier.padding(start = 16.dp)) {

                        Column {
                            Text(
                                text = "Cast",
                                style = TextStyle(
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            LazyVerticalGrid(
                                columns = GridCells.Fixed(3),
                                modifier = Modifier.height(500.dp)
                            ) {
                                var size = credits?.cast?.size ?: 0
                                if (size > 6) {
                                    size = 6
                                }
                                items(size) { index ->
                                    val cast = credits?.cast?.get(index)

                                    Column {

                                        Box(modifier = Modifier.clip(shape = RoundedCornerShape(6.dp))) {
                                            if (cast?.profilePath != null) {
                                                val imageUrl =
                                                    "https://image.tmdb.org/t/p/w185${cast.profilePath}"



                                                AsyncImage(
                                                    model = imageUrl,
                                                    contentDescription = "cast",
                                                    modifier = Modifier
                                                        .scale(1.15F)
                                                        .height(150.dp)

                                                )
                                            } else {
                                                Box(
                                                    modifier = Modifier.clip(
                                                        shape = RoundedCornerShape(
                                                            6.dp
                                                        )
                                                    )
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Filled.Person,
                                                        contentDescription = "",
                                                        modifier = Modifier
                                                            .height(150.dp)
                                                            .scale(1.5F)
                                                            .aspectRatio(0.665F)
                                                    )
                                                }
                                            }

                                        }
                                        Text(text = "${cast?.name}")
                                        Spacer(modifier = Modifier.height(8.dp))
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
                    )
                }
            }


        }
    }
}

@Composable
private fun Details(label: String, value: String, icon: ImageVector) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 12.dp, start = 4.dp, end = 4.dp)
    ) {
        Icon(imageVector = icon, contentDescription = "")
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = buildAnnotatedString {

                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(label)
                }
                append(value)

            },
            style = MaterialTheme.typography.bodyMedium,

            )
    }
}

@Composable
fun CheckWatchlist(watchlistViewModel: WatchlistViewModel = hiltViewModel()) {
    Button(
        onClick = {

            watchlistViewModel.getWatchlist()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.LightGray
        ),
        shape = RoundedCornerShape(6.dp)
    ) {
        Text(text = "log watchlist")


    }
}

@SuppressLint("DefaultLocale")
fun formatLargeNumber(number: Long): String {
    return when {
        number >= 1_000_000_000 -> String.format("%.1fB", number / 1_000_000_000.0)
        number >= 1_000_000 -> String.format("%.1fM", number / 1_000_000.0)
        number >= 1_000 -> String.format("%.1fK", number / 1_000.0)
        else -> number.toString()
    }
}
