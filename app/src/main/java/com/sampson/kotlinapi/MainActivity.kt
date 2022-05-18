package com.sampson.kotlinapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sampson.kotlinapi.controller.MovieApplication
import com.sampson.kotlinapi.model.MovieViewModel
import com.sampson.kotlinapi.model.MovieViewModelFactory

class MainActivity : AppCompatActivity() {

    private val movieViewModel: MovieViewModel by viewModels {
        MovieViewModelFactory((application as MovieApplication).movieRepository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getMovies()
    }

    private fun getMovies() {

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