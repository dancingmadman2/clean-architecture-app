
import com.example.myapplication.data.remote.MovieApi
import com.example.myapplication.data.remote.dto.movie_detail.MovieDetailDto
import com.example.myapplication.data.remote.dto.movie.MovieDto
import com.example.myapplication.data.remote.dto.movie_detail.toMovieDetail
import com.example.myapplication.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi,


): MovieRepository {

    override suspend fun getMovies(): List<MovieDto> {
        return api.getMovies().results
    }

    override suspend fun getMovieDetail(movieId: Int): MovieDetailDto {
        return api.getMovieDetail(movieId)
    }

}

