package com.vineeth.movies.entitys

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity
@TypeConverters(MoviesListConverter::class)
data class HomeData(
    val genre: String,
    @PrimaryKey
    val id: String,
    var movieslist: List<Movieslist>?,
    val type: Int
){
    override fun toString(): String {
        return genre;
    }
}

class MoviesListConverter {
    @TypeConverter
    fun fromList(movies: List<Movieslist>): String {
        return Gson().toJson(movies)
    }

    @TypeConverter
    fun toList(moviesString: String): List<Movieslist> {
        val listType = object : TypeToken<List<Movieslist>>() {}.type
        return Gson().fromJson(moviesString, listType)
    }
}