package com.example.movieTracker.data.remote.dto.movie_detail.overview

import com.google.gson.annotations.SerializedName


data class Genre(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)