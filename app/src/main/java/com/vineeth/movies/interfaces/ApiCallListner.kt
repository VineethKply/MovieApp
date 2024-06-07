package com.vineeth.movies.interfaces

interface ApiCallListner {
    fun onApiCallStart()
    fun onApiCallSuccess()
    fun onApiCallError()
}