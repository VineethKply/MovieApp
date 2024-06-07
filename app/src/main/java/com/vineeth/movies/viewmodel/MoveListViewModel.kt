package com.vineeth.movies.viewmodel

import android.content.Context
import androidx.lifecycle.*
import com.vineeth.movies.database.AppDatabase
import com.vineeth.movies.entitys.HomeData
import com.vineeth.movies.entitys.Movieslist
import com.vineeth.movies.interfaces.ApiCallListner
import com.vineeth.movies.repositry.MovieRepositry
import kotlinx.coroutines.launch


class MoveListViewModel(
    private val repository: MovieRepositry,
    private val apiCallListner: ApiCallListner
) : ViewModel() {
    fun fetchingMoveList() = viewModelScope.launch {
        repository.fetchMoviesFromWeb(apiCallListner)
    }

    fun getMovieListByTypeID(typeID: String): LiveData<List<Movieslist>> {
        if(typeID.equals("0",true)){
            return allMovies
        }else{
            return repository.getMovieList(typeID)
        }
    }

    val allMovies: LiveData<List<Movieslist>> = repository.allMovies

    val allHomeData: LiveData<List<HomeData>> = repository.allHomeData


}

class MoveListViewModelFactory(
    private val context: Context,
    private val apiCallListner: ApiCallListner
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoveListViewModel::class.java)) {
            val appDatabase = AppDatabase.getInstance(context)
            val repository = appDatabase.moveListDao()?.let { MovieRepositry(it) }
            return repository?.let { MoveListViewModel(it, apiCallListner) } as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
