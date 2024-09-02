package com.example.movieTracker.data.remote

import com.example.movieTracker.data.remote.dto.movie.MovieDto
import com.example.movieTracker.data.remote.dto.movie_detail.credits.MovieCreditsDto
import com.example.movieTracker.data.remote.dto.movie_detail.overview.MovieDetailDto

interface RemoteDataSource {
    suspend fun getMovies(): List<MovieDto>
    suspend fun getMovieDetail(movieId: Int): MovieDetailDto
    suspend fun getMovieCredits(movieId: Int): MovieCreditsDto
}