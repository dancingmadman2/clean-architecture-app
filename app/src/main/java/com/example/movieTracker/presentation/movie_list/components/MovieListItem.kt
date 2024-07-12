package com.example.movieTracker.presentation.movie_list.components


import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.movieTracker.domain.model.Movie
import com.example.movieTracker.presentation.watchlist.components.BookmarkButton

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MovieListItem(
    movie: Movie,
    onItemClick: (Movie) -> Unit,
) {
    val imageUrl =
        "https://image.tmdb.org/t/p/w185${movie.posterPath}" // imageUrl movie dataclassina tasinacak

    Column(
        modifier = Modifier

            .clickable { onItemClick(movie) }
            .padding(16.dp),
        verticalArrangement = Arrangement.Center


    ) {


        Row() {

            AsyncImage(
                model = imageUrl,
                contentDescription = "Movie Poster",
                //contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))


            )
            Spacer(modifier = Modifier.width(20.dp))
            Column {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = movie.title, modifier = Modifier
                                .padding(top = 8.dp),
                            style = TextStyle(
                                fontSize = 20.sp
                            )
                        )
                    }
                    BookmarkButton(movieId = movie.id)
                }
                Text(
                    text = if (movie.overview.length < 100) movie.overview else "${
                        movie.overview.substring(
                            0,
                            100
                        )
                    }...",
                    modifier = Modifier.padding(top = 8.dp),
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        fontSize = 11.sp
                    )
                )

            }


        }

        //Text(text = movie.releaseDate, modifier = Modifier.padding(top = 4.dp))

    }
}

