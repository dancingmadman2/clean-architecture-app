package com.example.movieTracker.data.remote.dto.movie_detail


import com.google.gson.annotations.SerializedName


data class BelongsToCollection(
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("poster_path")
    val posterPath: String
)