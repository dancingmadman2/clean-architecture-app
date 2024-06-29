package com.example.movieTracker.domain.model

import com.example.movieTracker.data.remote.dto.movie_detail.Genre
import com.example.movieTracker.data.remote.dto.movie_detail.ProductionCompany
import com.example.movieTracker.data.remote.dto.movie_detail.ProductionCountry


data class MovieDetail(
    val adult: Boolean,
    val backdropPath: String,
    val budget: Int,
    val genres: List<Genre>,
    val id: Int,
    val originCountry: List<String>,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val productionCompanies: List<ProductionCompany>,
    val productionCountries: List<ProductionCountry>,
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val title: String,
    val voteAverage: Double,
    val voteCount: Int
)
