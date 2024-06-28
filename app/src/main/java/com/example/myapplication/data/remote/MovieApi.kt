package com.example.myapplication.data.remote

import com.example.myapplication.BuildConfig
import com.example.myapplication.data.remote.dto.movie_detail.MovieDetailDto
import com.example.myapplication.data.remote.dto.movie.MovieDto
import com.example.myapplication.data.remote.dto.movie.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("/3/discover/movie")
    suspend fun getMovies(@Query("api_key") apiKey: String = BuildConfig.MOVIE_API_KEY): List<MovieDto>

    @GET("movies/{id}")
    suspend fun getMovieDetail(@Path("id") movieId: Int,@Query("api_key") apiKey: String = BuildConfig.MOVIE_API_KEY): MovieDetailDto

}
