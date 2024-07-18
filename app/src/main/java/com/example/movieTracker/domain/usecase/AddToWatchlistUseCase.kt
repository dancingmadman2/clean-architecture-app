package com.example.movieTracker.domain.usecase

import com.example.movieTracker.domain.repository.MovieRepository
import javax.inject.Inject


class AddToWatchlistUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    suspend operator fun invoke(movieId: Int) {
        movieRepository.addToWatchlist(movieId)
    }
}
