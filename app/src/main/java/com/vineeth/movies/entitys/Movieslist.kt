package com.vineeth.movies.entitys

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Entity
@TypeConverters(StringListConverter::class)
data class Movieslist(
    val typeid: String="",
    val desc: String,
    val genre: List<String>,

    @PrimaryKey
    val id: String,
    val posterurl: String,
    val rating: String,
    val release: String,
    val title: String
)

class StringListConverter {
    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return value?.joinToString(",")
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        return value?.split(",")?.map { it.trim() }
    }
}