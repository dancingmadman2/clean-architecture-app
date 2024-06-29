
package com.example.movieTracker.presentation.movie_list.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.movieTracker.domain.model.Movie

@Composable
fun MovieListItem(
    movie: Movie,
    onItemClick: (Movie) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(movie) }
            .padding(16.dp)
        
    ) {
        val imageUrl = "https://image.tmdb.org/t/p/w185${movie.posterPath}"
        Log.d("MovieListItem", "Image URL: $imageUrl")

        AsyncImage(
            model = imageUrl,
            contentDescription = "Movie Poster",
            modifier = Modifier.height(200.dp)
        )
        Text(text = movie.title, modifier = Modifier.padding(top = 8.dp))
        //Text(text = movie.releaseDate, modifier = Modifier.padding(top = 4.dp))
        HorizontalDivider()
    }
}

