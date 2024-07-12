package com.example.movieTracker.presentation.movie_list.components

import android.util.Log
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.movieTracker.domain.model.Movie

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.MovieListGridItem(
    movie: Movie,
    onItemClick: (Movie) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(movie) }
            .padding(16.dp)

    ) {
        val imageUrl = "https://image.tmdb.org/t/p/w185${movie.posterPath}"
        Log.d("MovieListItem", "Image URL: $imageUrl")

        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(6.dp))
                .fillMaxSize()
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .scale(1f)
                    .height(200.dp)
                    .sharedElement(
                        state = rememberSharedContentState(key = "poster/${movie.id}"),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = { _, _ ->

                            tween(durationMillis = 1000)
                        }

                    )
            )
        }
        Text(text = movie.title, modifier = Modifier
            .padding(top = 8.dp)
            .sharedElement(
                state = rememberSharedContentState(key = "title/${movie.id}"),
                animatedVisibilityScope = animatedVisibilityScope,
                boundsTransform = { _, _ ->

                    tween(durationMillis = 1000)
                }

            )
        )
        //Text(text = movie.releaseDate, modifier = Modifier.padding(top = 4.dp))
        //HorizontalDivider()
    }
}

