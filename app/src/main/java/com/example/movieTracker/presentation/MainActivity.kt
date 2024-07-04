package com.example.movieTracker.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movieTracker.common.Constants
import com.example.movieTracker.presentation.movie_detail.MovieDetailScreen
import com.example.movieTracker.presentation.movie_list.MovieListScreen
import com.example.movieTracker.presentation.watchlist.WatchlistScreen
import com.example.movieTracker.ui.components.BotBar
import com.example.movieTracker.ui.theme.MovieTrackerTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        enableEdgeToEdge()
        setContent {


            MovieTrackerTheme {
                var selectedIndex by remember { mutableIntStateOf(0) }
                val navController = rememberNavController()
                var showBottomBar by remember { mutableStateOf(true) }
                Scaffold(
                    //contentWindowInsets = WindowInsets.navigationBars,
                    bottomBar = {
                        if (showBottomBar)
                            BotBar { index ->
                                selectedIndex = index

                                when (index) {
                                    0 -> navController.navigate(Screen.MovieListScreen.route)
                                    1 -> navController.navigate(Screen.WatchlistScreen.route)
                                }


                            }
                    }
                ) { innerPadding ->

                    Surface(
                        color = Color.White,
                        modifier = Modifier.padding(innerPadding)

                    ) {


                        NavHost(
                            navController = navController,
                            startDestination = Screen.MovieListScreen.route
                        ) {
                            composable(
                                route = Screen.MovieListScreen.route
                            ) {
                                MovieListScreen(navController)
                                showBottomBar = true
                            }
                            composable(
                                route = Screen.MovieDetailScreen.route + "/{${Constants.MOVIE_ID}}"
                            ) {

                                MovieDetailScreen(navController)
                                showBottomBar = false
                            }
                            composable(
                                route = Screen.WatchlistScreen.route
                            ) {
                                WatchlistScreen(navController)
                                showBottomBar = true
                            }
                        }


                    }

                }


            }
        }
    }
}


