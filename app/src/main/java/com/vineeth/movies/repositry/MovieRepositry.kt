package com.vineeth.movies.repositry


import androidx.lifecycle.LiveData
import com.vineeth.movies.dao.MovieListDao
import com.vineeth.movies.entitys.HomeData
import com.vineeth.movies.entitys.Movies
import com.vineeth.movies.entitys.Movieslist
import com.vineeth.movies.interfaces.ApiCallListner
import com.vineeth.movies.network.MovieRetofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepositry(private val movieListDao: MovieListDao) {
    private val movieRetofitClient = MovieRetofitClient()

    val allMovies: LiveData<List<Movieslist>> = movieListDao.getAllMovies()

    val allHomeData: LiveData<List<HomeData>> = movieListDao.getAllHomeData()

    fun getMovieList(TypeID:String): LiveData<List<Movieslist>> {
        return movieListDao.getAllMovies(TypeID)
    }

    fun fetchMoviesFromWeb(apiCallListner: ApiCallListner){
        apiCallListner.onApiCallStart()
        movieRetofitClient.fetchMovieList().enqueue(object : Callback<Movies> {
            override  fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                if (response.isSuccessful) {
                    apiCallListner.onApiCallSuccess()
                    response.body()?.let {
                        movieListDao.saveAll(it.homeData)
                        for(homeData in  it.homeData) {
                            homeData.movieslist= homeData.movieslist!!.map { it.copy(typeid = homeData.id) }
                            movieListDao.saveAllMovieList(homeData.movieslist!!)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<Movies>, t: Throwable) {
                apiCallListner.onApiCallError()
            }
        })

    }

}

