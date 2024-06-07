package com.vineeth.movies.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vineeth.movies.dao.MovieListDao
import com.vineeth.movies.entitys.HomeData
import com.vineeth.movies.entitys.Movieslist

@Database(
    entities = [ HomeData::class,Movieslist::class],
    version = 1,
    exportSchema = true
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun moveListDao() : MovieListDao?

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "Movies")
                .allowMainThreadQueries().build()
        }
    }

}