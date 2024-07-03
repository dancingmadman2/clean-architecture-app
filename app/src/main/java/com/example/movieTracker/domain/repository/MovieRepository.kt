package com.example.movieTracker.domain.repository

import com.example.movieTracker.data.remote.dto.movie.MovieDto
import com.example.movieTracker.data.remote.dto.movie_detail.credits.MovieCreditsDto
import com.example.movieTracker.data.remote.dto.movie_detail.overview.MovieDetailDto
import com.example.movieTracker.domain.model.Movie


interface MovieRepository {
    suspend fun getMovies(): List<MovieDto>
    suspend fun getMovieDetail(movieId: Int): MovieDetailDto
    suspend fun getMovieCredits(movieId: Int): MovieCreditsDto
    suspend fun getMovieById(movieId: Int): Movie?
}
