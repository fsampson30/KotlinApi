package com.sampson.kotlinapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sampson.kotlinapi.controller.MovieApplication
import com.sampson.kotlinapi.model.MovieViewModel

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getMovies()
    }

    private fun getMovies() {
        val movieRepository = (application as MovieApplication).movieRepository
        val movieViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MovieViewModel(movieRepository) as T
            }

        }).get(MovieViewModel::class.java)
        movieViewModel.fetchPopularMovies()
        movieViewModel.popularMovies.observe(this) { popularMovies ->
            Log.d("FLAVIO", popularMovies[0].title)
        }

        movieViewModel.fetchTopRatedMovies()
        movieViewModel.topRated.observe(this) { topRated ->
            Log.d("FLAVIO", topRated[0].title)
        }

        movieViewModel.error.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT ).show()
        }
    }
}