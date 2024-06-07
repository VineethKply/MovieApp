package com.vineeth.movies.interfaces

import com.vineeth.movies.entitys.Movieslist

interface MovieClickListner {

    fun onMovieClick(movie: Movieslist)
}