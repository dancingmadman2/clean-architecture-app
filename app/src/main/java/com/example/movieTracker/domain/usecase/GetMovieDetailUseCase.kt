package com.example.movieTracker.domain.usecase

import android.annotation.SuppressLint
import android.net.http.HttpException
import com.example.movieTracker.common.Resource
import com.example.movieTracker.data.remote.dto.movie_detail.toMovieDetail
import com.example.movieTracker.domain.model.MovieDetail
import com.example.movieTracker.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val repository: MovieRepository
){
    operator fun invoke(movieId: Int): Flow<Resource<MovieDetail>> = flow{
        try{
            emit(Resource.Loading())
            val movieDetail= repository.getMovieDetail(movieId).toMovieDetail()
            emit(Resource.Success(movieDetail))
        }catch(@SuppressLint("NewApi") e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "Errormania occured."))
        } catch(e: IOException ){
            emit(Resource.Error("check connection"))
        }
    }
}