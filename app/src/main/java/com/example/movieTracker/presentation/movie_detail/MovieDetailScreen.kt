package com.example.movieTracker.presentation.movie_detail

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Timelapse
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
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
import com.example.movieTracker.common.SetSystemBarsColor
import com.example.movieTracker.presentation.movie_credits.MovieCreditsViewModel
import com.example.movieTracker.presentation.movie_detail.components.MovieDetailShimmer
import com.example.movieTracker.presentation.movie_detail.components.RatingBar
import com.example.movieTracker.presentation.watchlist.WatchlistViewModel
import com.example.movieTracker.ui.components.TopBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.MovieDetailScreen(
    navController: NavController,
    viewModel: MovieDetailViewModel = hiltViewModel(),
    creditsViewModel: MovieCreditsViewModel = hiltViewModel(),
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    SetSystemBarsColor(statusBarColor = Color.Black, navigationBarColor = Color.Black)

    val state by viewModel.state.collectAsState()

    var imageAlpha by remember { mutableFloatStateOf(1f) }
    var imageScale by remember { mutableFloatStateOf(1f) }
    val scrollState = rememberScrollState()


    val showBox by remember(scrollState) {
        derivedStateOf {
            if (scrollState.value <= 10) {
                true
            } else {
                scrollState.value > 500
            }

        }
    }

    LaunchedEffect(scrollState) {
        snapshotFlow { scrollState.value }
            .collect { value ->
                val newScale = maxOf(0f, 1f - value.toFloat() / 1000f)
                if (newScale != imageScale) {
                    imageScale = newScale
                }
                val newAlpha = maxOf(0f, 1f - value.toFloat() / 1000f)
                if (newAlpha != imageAlpha) {
                    imageAlpha = newAlpha
                }
            }
    }


    val movie = state.movie
    val credits = creditsViewModel.state.value.movieCredits

    val posterUrl =
        "https://image.tmdb.org/t/p/w185${movie?.posterPath}"

    Box(modifier = Modifier.fillMaxSize()) {

        if (!state.isLoading) {
            AsyncImage(
                model = posterUrl,
                contentDescription = "movie poster",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .blur(50.dp)
                    .alpha(0.7f)
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .drawBehind {
                        drawRect(Color.Black.copy(alpha = 0.4f))
                    }
            )
        }
        Scaffold(
            topBar = {
                if (movie != null) {
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        TopBar(
                            title = movie.title,
                            navController = navController,
                            showBookmark = true,
                            movieId = movie.id,
                            transparent = true,
                            collapseAppbar = false
                        )
                    }
                }
            },
            containerColor = Color.Transparent


        ) { innerPadding ->


            Column(
                modifier = Modifier
                    .verticalScroll(
                        scrollState
                    )
                    .padding(innerPadding)

            ) {


                Spacer(modifier = Modifier.height(10.dp))

                if (state.isLoading) {
                    MovieDetailShimmer()
                } else {
                    Row(Modifier.padding(16.dp)) {

                        Box(
                            modifier = Modifier
                                .clip(shape = RoundedCornerShape(6.dp))
                                .weight(0.8f)
                        ) {

                            AsyncImage(
                                model = posterUrl,
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
                                    .alpha(imageAlpha)
                                //.scale(imageScale)
                            )
                        }
                        Spacer(modifier = Modifier.width(24.dp))
                        Column(modifier = Modifier.weight(0.8f)) {

                            Spacer(modifier = Modifier.height(12.dp))
                            Details(
                                "Release Date: ",
                                "${movie?.releaseDate}",
                                Icons.Filled.DateRange
                            )
                            //   Details("Genre: ", "${movie?.genres?.get(0)?.name}", Icons.Filled.Info)
                            Details(
                                "Runtime : ",
                                "${movie?.runtime} min.",
                                Icons.Filled.Timelapse
                            )
                            /*
                                                        Details(
                                                            label = "Production: ",
                                                            value = "${movie?.productionCompanies?.get(0)?.name}",
                                                            icon = Icons.Filled.Movie
                                                        )

                             */
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
                                fontWeight = FontWeight.Bold, fontSize = 24.sp,
                                color = Color.White
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

                            ),
                            color = Color.White
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
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                )

                                Spacer(modifier = Modifier.height(12.dp))
                                Row {
                                    Column {

                                        Box(
                                            modifier = Modifier.clip(
                                                shape = RoundedCornerShape(
                                                    6.dp
                                                )
                                            )
                                        ) {
                                            if (credits?.crew?.get(0)?.profilePath != null) {
                                                val directorUrl =
                                                    "https://image.tmdb.org/t/p/w185${credits.crew[0].profilePath}"
                                                AsyncImage(
                                                    model = directorUrl,
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
                                        Spacer(modifier = Modifier.height(5.dp))
                                        Text(
                                            text = "${credits?.crew?.get(0)?.name}",
                                            style = TextStyle(
                                                color = Color.White
                                            )
                                        )
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
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
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

                                        Box(
                                            modifier = Modifier.clip(
                                                shape = RoundedCornerShape(
                                                    6.dp
                                                )
                                            )
                                        ) {
                                            if (cast?.profilePath != null) {
                                                val castUrl =
                                                    "https://image.tmdb.org/t/p/w185${cast.profilePath}"



                                                AsyncImage(
                                                    model = castUrl,
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
                                        Spacer(modifier = Modifier.height(5.dp))
                                        Text(
                                            text = "${cast?.name}",
                                            style = TextStyle(color = Color.White)
                                        )
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
private fun Details(
    label: String,
    value: String,
    icon: ImageVector,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 12.dp, start = 4.dp, end = 4.dp)
    ) {
        Icon(
            imageVector = icon, contentDescription = "", tint = Color.White
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = buildAnnotatedString {

                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.White)) {
                    append(label)
                }
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        color = Color.LightGray
                    )
                ) {
                    append(value)
                }


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