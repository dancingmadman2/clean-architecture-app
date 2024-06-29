package com.example.movieTracker.presentation

import MovieListScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movieTracker.common.Constants
import com.example.movieTracker.presentation.movie_detail.MovieDetailScreen
//import com.example.myapplication.presentation.movie_detail.MovieDetailScreen
import com.example.movieTracker.ui.theme.MovieTrackerTheme
// import com.example.myapplication.presentation.movie_list.MovieListScreen
// import com.example.myapplication.ui.theme.MovieTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieTrackerTheme {
                Surface(color = Color.White ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.MovieListScreen.route
                    ) {
                        composable(
                            route = Screen.MovieListScreen.route
                        ) {
                            MovieListScreen(navController)
                        }
                        composable(
                            route = Screen.MovieDetailScreen.route + "/{${Constants.MOVIE_ID}}"
                        ) {

                            MovieDetailScreen()
                        }
                    }
                }
                }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

