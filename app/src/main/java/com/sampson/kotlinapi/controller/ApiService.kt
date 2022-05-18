package com.sampson.kotlinapi.controller

import com.sampson.kotlinapi.model.PopularMoviesReponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") apiKey: String) :Observable<PopularMoviesReponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("api_key") apiKey: String) : Observable<PopularMoviesReponse>
}