package com.example.movieTracker.domain.repository

import com.example.movieTracker.data.remote.dto.movie_detail.MovieDetailDto
import com.example.movieTracker.data.remote.dto.movie.MovieDto



interface MovieRepository {
    suspend fun getMovies(): List<MovieDto>
    suspend fun getMovieDetail(movieId: Int): MovieDetailDto
}
