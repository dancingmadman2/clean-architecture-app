import android.util.Log
import com.example.myapplication.presentation.movie_list.MovieListViewModel



import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.presentation.Screen
import com.example.myapplication.presentation.movie_list.components.MovieListItem


@Composable
fun MovieListScreen(
    navController: NavController,
    viewModel: MovieListViewModel = hiltViewModel(),
){
    val state = viewModel.state.value

    Box(modifier = Modifier.fillMaxSize()){

        LazyColumn (modifier = Modifier.fillMaxSize()) {

            items(state.movies){movie->
                MovieListItem(
                    movie  = movie,
                    onItemClick = {
                    //    Log.d("MOVIE_ID","${movie.id}")
                        navController.navigate(Screen.MovieDetailScreen.route + "/${movie.id}")
                    }
                )
            }


        }
        if(state.error.isNotBlank()) {
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
        if(state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
    }

