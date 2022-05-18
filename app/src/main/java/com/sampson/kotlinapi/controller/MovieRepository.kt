package com.sampson.kotlinapi.controller

class MovieRepository (private val apiService: ApiService) {
    private val apiKey = "22fff89ba91cea9db070ea96e0aa4451"

    fun fetchMovies() = apiService.getPopularMovies(apiKey)

    fun fetchTopRated() = apiService.getTopRatedMovies(apiKey)
}