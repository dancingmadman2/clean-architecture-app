package com.example.movieTracker.data.repository

import com.example.movieTracker.data.remote.MovieApi
import com.example.movieTracker.data.remote.dto.movie.MovieDto
import com.example.movieTracker.data.remote.dto.movie.toMovie
import com.example.movieTracker.data.remote.dto.movie_detail.credits.MovieCreditsDto
import com.example.movieTracker.data.remote.dto.movie_detail.overview.MovieDetailDto
import com.example.movieTracker.domain.model.Movie
import com.example.movieTracker.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi,


    ) : MovieRepository {

    override suspend fun getMovies(): List<MovieDto> {
        return api.getMovies().results
    }

    override suspend fun getMovieDetail(movieId: Int): MovieDetailDto {
        return api.getMovieDetail(movieId)
    }

    override suspend fun getMovieCredits(movieId: Int): MovieCreditsDto {
        return api.getMovieCredits(movieId)
    }

    override suspend fun getMovieById(movieId: Int): Movie? {
        return getMovies().find { it.id == movieId }?.toMovie()
    }

}

