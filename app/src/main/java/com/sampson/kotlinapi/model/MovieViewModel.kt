package com.sampson.kotlinapi.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sampson.kotlinapi.controller.MovieRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.IllegalArgumentException

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private val popularMoviesLiveData = MutableLiveData<List<Movie>>()
    private val errorLiveData = MutableLiveData<String>()
    private val topRatedMoviesLiveData = MutableLiveData<List<Movie>>()

    val popularMovies: LiveData<List<Movie>>
        get() = popularMoviesLiveData

    val error: LiveData<String>
        get() = errorLiveData

    val topRated: LiveData<List<Movie>>
        get() = topRatedMoviesLiveData

    private var disposable = CompositeDisposable()

    fun fetchPopularMovies() {
        disposable.add(movieRepository.fetchMovies()
            .subscribeOn(Schedulers.io())
            .map { it.results }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                popularMoviesLiveData.postValue(it)
            }, { error ->
                Log.d("ModelViewModel", "error encountered: ${error.localizedMessage}")
            })
        )
    }

    fun fetchTopRatedMovies() {
        disposable.add(movieRepository.fetchTopRated()
            .subscribeOn(Schedulers.io())
            .map { it.results }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                topRatedMoviesLiveData.postValue(it)
            }, { error ->
                Log.d("ModelViewModel", "error encountered: ${error.localizedMessage}")
            })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}

class MovieViewModelFactory(private val repository: MovieRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)){
            return MovieViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}