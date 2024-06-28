package com.example.myapplication.domain.usecase

import android.annotation.SuppressLint
import android.net.http.HttpException
import com.example.myapplication.common.Resource
import com.example.myapplication.data.remote.dto.movie.MovieResponse
import com.example.myapplication.data.remote.dto.movie.toMovie
import com.example.myapplication.domain.model.Movie
import com.example.myapplication.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject


class GetMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
){
operator fun invoke(): Flow<Resource<List<Movie>>> = flow{
    try{
        emit(Resource.Loading())
        val movies= repository.getMovies().map{ it.toMovie()}
        emit(Resource.Success(movies))
    }catch(@SuppressLint("NewApi") e: HttpException){
        emit(Resource.Error(e.localizedMessage ?: "Errormania occured."))
    } catch(e:IOException ){
        emit(Resource.Error("check connection"))
    }
}
}
