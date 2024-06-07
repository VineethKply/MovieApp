package com.vineeth.movies.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vineeth.movies.entitys.HomeData
import com.vineeth.movies.entitys.Movieslist


@Dao
interface MovieListDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveAll(homeData: List<HomeData>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveAllMovieList(movieList: List<Movieslist>)

    @Query("SELECT * FROM Movieslist")
    fun getAllMovies(): LiveData<List<Movieslist>>

    @Query("SELECT * FROM HomeData")
    fun getAllHomeData(): LiveData<List<HomeData>>

    @Query("SELECT * FROM Movieslist where typeid=:typeId")
    fun getAllMovies(typeId:String): LiveData<List<Movieslist>>
}