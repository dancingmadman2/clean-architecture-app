package com.example.movieTracker.domain.usecase

import com.example.movieTracker.domain.repository.MovieRepository
import javax.inject.Inject


class LoadWatchlistUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    suspend operator fun invoke(): Set<Int> {
        return movieRepository.loadWatchlist()
    }
}