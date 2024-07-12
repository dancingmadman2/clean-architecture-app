package com.example.movieTracker.data.remote

import com.example.movieTracker.data.remote.dto.movie.MovieDto
import com.example.movieTracker.data.remote.dto.movie_detail.credits.MovieCreditsDto
import com.example.movieTracker.data.remote.dto.movie_detail.overview.MovieDetailDto
import javax.inject.Inject

class MovieApiRemoteDataSource @Inject constructor(
    private val api: MovieApi,
) : RemoteDataSource {

    override suspend fun getMovies(): List<MovieDto> {
        return api.getMovies().results
    }

    override suspend fun getMovieDetail(movieId: Int): MovieDetailDto {
        return api.getMovieDetail(movieId)
    }

    override suspend fun getMovieCredits(movieId: Int): MovieCreditsDto {
        return api.getMovieCredits(movieId)
    }

}

