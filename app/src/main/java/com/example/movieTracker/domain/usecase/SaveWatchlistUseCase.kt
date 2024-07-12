package com.example.movieTracker.domain.usecase

import com.example.movieTracker.domain.repository.MovieRepository
import javax.inject.Inject

class SaveWatchlistUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    suspend operator fun invoke(watchlist: Set<Int>) {
        movieRepository.saveWatchlist(watchlist)
    }
}
