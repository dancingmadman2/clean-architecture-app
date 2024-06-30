package com.example.movieTracker.domain.model

import com.example.movieTracker.data.remote.dto.movie_detail.credits.Cast
import com.example.movieTracker.data.remote.dto.movie_detail.credits.Crew


data class MovieCredits(
    val cast: List<Cast>,
    val crew: List<Crew>
)