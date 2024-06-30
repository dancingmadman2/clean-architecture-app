import com.example.movieTracker.data.remote.MovieApi
import com.example.movieTracker.data.remote.dto.movie.MovieDto
import com.example.movieTracker.data.remote.dto.movie_detail.credits.MovieCreditsDto
import com.example.movieTracker.data.remote.dto.movie_detail.overview.MovieDetailDto
import com.example.movieTracker.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi,


    ) : MovieRepository {

    override suspend fun getMovies(): List<MovieDto> {
        return api.getMovies().results
    }

    override suspend fun getMovieDetail(movieId: Int): MovieDetailDto {
        val response = api.getMovieDetail(movieId)
        return api.getMovieDetail(movieId)
    }

    override suspend fun getMovieCredits(movieId: Int): MovieCreditsDto {
        val response = api.getMovieCredits(movieId)
        return api.getMovieCredits(movieId)
    }

}

