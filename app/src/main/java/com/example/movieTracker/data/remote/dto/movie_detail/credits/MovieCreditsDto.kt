package com.example.movieTracker.data.remote.dto.movie_detail.credits

import com.example.movieTracker.domain.model.MovieCredits
import com.google.gson.annotations.SerializedName


data class MovieCreditsDto(
    @SerializedName("cast")
    val cast: List<Cast>,
    @SerializedName("crew")
    val crew: List<Crew>,
    @SerializedName("id")
    val id: Int
)

fun MovieCreditsDto.toMovieCredits(): MovieCredits {

    return MovieCredits(
        cast = cast,
        crew = crew,
    )

}