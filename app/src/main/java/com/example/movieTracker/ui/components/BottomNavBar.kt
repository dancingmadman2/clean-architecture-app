package com.example.movieTracker.ui.components


import androidx.compose.foundation.layout.height
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BotBar(
    onItemSelected: (Int) -> Unit
) {
    val items = listOf("Movies", "To Watch")
    var selectedIndex by remember { mutableIntStateOf(0) }



    Surface(
        modifier = Modifier
            .height(80.dp)
    ) {
        BottomNavigation(
            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = Color.White,
        ) {

            items.forEachIndexed { index, item ->
                BottomNavigationItem(

                    icon = {
                        when (index) {

                            0 -> Icon(Icons.Filled.Movie, contentDescription = "Movies")
                            1 -> Icon(Icons.Filled.Bookmark, contentDescription = "Watchlist")
                            else -> {}
                        }
                    },
                    label = { Text(item) },
                    selected = selectedIndex == index,
                    onClick = {
                        selectedIndex = index
                        onItemSelected(index)
                    }
                )
            }
        }
    }
}
