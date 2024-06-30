package com.example.movieTracker.presentation.movie_detail

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.filled.Timelapse
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.movieTracker.ui.components.TopBar


@Composable
fun MovieDetailScreen(
    navController: NavController,
    viewModel: MovieDetailViewModel = hiltViewModel(),
    creditsViewModel: MovieCreditsViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val movie = state.movie
    val credits = creditsViewModel.state.value.movieCredits

    Box(modifier = Modifier.fillMaxSize()) {


        Column() {
            TopBar(
                title = "${movie?.title}",

                navController = navController
            )
            Spacer(modifier = Modifier.height(20.dp))




            Row(Modifier.padding(16.dp)) {
                Box(modifier = Modifier.clip(shape = RoundedCornerShape(18.dp))) {
                    val imageUrl =
                        "https://image.tmdb.org/t/p/w185${movie?.posterPath}"
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Movie Poster",
                        modifier = Modifier
                            .scale(1.15F)
                            .height(300.dp)
                    )
                }
                Spacer(modifier = Modifier.width(24.dp))
                Column {
                    Text(
                        text = "${movie?.title}",
                        style = TextStyle(
                            fontSize = 24.sp,
                        ),
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Details("Release Date: ", "${movie?.releaseDate}", Icons.Filled.DateRange)
                    Details("Genre: ", "${movie?.genres?.get(0)?.name}", Icons.Filled.Info)
                    Details("Runtime : ", "${movie?.runtime} min.", Icons.Filled.Timelapse)

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Overview",
                        style = TextStyle(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${movie?.overview}",
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(
                            fontSize = 12.sp
                        )
                    )

                }
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
                        Column {

                            Box(modifier = Modifier.clip(shape = RoundedCornerShape(18.dp))) {
                                if (credits?.crew?.get(0)?.profilePath != null) {
                                    val imageUrl =
                                        "https://image.tmdb.org/t/p/w185${credits.crew.get(0).profilePath}"
                                    AsyncImage(
                                        model = imageUrl,
                                        contentDescription = "Movie Poster",
                                        modifier = Modifier
                                            .scale(1.15F)
                                            .height(150.dp)
                                    )
                                } else {
                                    Box(modifier = Modifier.clip(shape = RoundedCornerShape(18.dp))) {
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

                    Column(modifier = Modifier.padding(16.dp)) {

                        Details(
                            label = "Production: ",
                            value = "${movie?.productionCompanies?.get(0)?.name}",
                            icon = Icons.Filled.Movie
                        )
                        Details(
                            label = "Budget: ",
                            value = "${movie?.budget} $",
                            icon = Icons.Filled.AttachMoney
                        )
                        Details(
                            label = "Revenue: ",
                            value = "${movie?.revenue} $",
                            icon = Icons.Filled.AttachMoney
                        )
                        Details(
                            label = "Popularity: ",
                            value = "${movie?.popularity}",
                            icon = Icons.Filled.People
                        )
                        Details(
                            label = "Rating: ",
                            value = "${movie?.voteAverage} / 10",
                            icon = Icons.Filled.StarRate
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

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
                    ) {
                        var size = credits?.cast?.size ?: 0
                        if (size > 6) {
                            size = 6
                        }
                        items(size) { index ->
                            val cast = credits?.cast?.get(index)

                            Column {

                                Box(modifier = Modifier.clip(shape = RoundedCornerShape(18.dp))) {
                                    if (cast?.profilePath != null) {
                                        val imageUrl =
                                            "https://image.tmdb.org/t/p/w185${cast.profilePath}"
                                        AsyncImage(
                                            model = imageUrl,
                                            contentDescription = "Movie Poster",
                                            modifier = Modifier
                                                .scale(1.15F)
                                                .height(150.dp)
                                        )
                                    } else {
                                        Box(modifier = Modifier.clip(shape = RoundedCornerShape(18.dp))) {
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

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
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

@Composable
private fun Details(label: String, value: String, icon: ImageVector) {
    Row {
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
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}