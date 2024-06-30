package com.example.movieTracker.data.remote

import com.example.movieTracker.BuildConfig
import com.example.movieTracker.data.remote.dto.movie.MoviesResponse
import com.example.movieTracker.data.remote.dto.movie_detail.credits.MovieCreditsDto
import com.example.movieTracker.data.remote.dto.movie_detail.overview.MovieDetailDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("/3/discover/movie")
    suspend fun getMovies(@Query("api_key") apiKey: String = BuildConfig.MOVIE_API_KEY): MoviesResponse

    @GET("/3/movie/{id}")
    suspend fun getMovieDetail(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.MOVIE_API_KEY
    ): MovieDetailDto


    @GET("/3/movie/{id}/credits")
    suspend fun getMovieCredits(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.MOVIE_API_KEY
    ): MovieCreditsDto


}
