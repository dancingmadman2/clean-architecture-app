package com.example.movieTracker.presentation.movie_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun MovieDetailShimmer() {
    val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.Window)

    Column(modifier = Modifier.padding(16.dp)) {
        Row(modifier = Modifier.shimmer(shimmerInstance)) {
            Box(
                modifier = Modifier
                    .size(150.dp, 240.dp)
                    .background(Color.Gray, RoundedCornerShape(6.dp))
            )

            Spacer(modifier = Modifier.width(24.dp))

            Column {
                Box(
                    modifier = Modifier
                        .height(30.dp)
                        .fillMaxWidth()
                        .background(Color.Gray, RoundedCornerShape(6.dp))
                )
                Spacer(modifier = Modifier.height(12.dp))

                repeat(6) {
                    Box(
                        modifier = Modifier
                            .height(20.dp)
                            .fillMaxWidth()
                            .background(Color.Gray, RoundedCornerShape(6.dp))
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .height(30.dp)
                .fillMaxWidth()
                .shimmer(shimmerInstance)
                .background(Color.Gray, RoundedCornerShape(6.dp))
        )
        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth()
                .shimmer(shimmerInstance)
                .background(Color.Gray, RoundedCornerShape(6.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .height(30.dp)
                .fillMaxWidth()
                .shimmer(shimmerInstance)
                .background(Color.Gray, RoundedCornerShape(6.dp))
        )
        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .height(500.dp)
                .shimmer(shimmerInstance)
        ) {
            items(12) {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .size(100.dp, 150.dp)
                            .background(Color.Gray, RoundedCornerShape(6.dp))
                            .padding(8.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .height(20.dp)
                            .width(100.dp)
                            .background(Color.Gray, RoundedCornerShape(6.dp))
                    )
                }
            }
        }
    }
}
