package com.vineeth.movies.network

import com.google.gson.GsonBuilder
import com.vineeth.movies.entitys.Movies
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit


interface MovieRetofitClient {

    companion object {
        private val retrofit: Retrofit? = null

        fun getRetrofit(): Retrofit {
            if (retrofit == null) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
                val okHttpClient: OkHttpClient = OkHttpClient.Builder()
                    .readTimeout(60 * 5, TimeUnit.SECONDS)
                    .connectTimeout(60 * 5, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .build()
                val gson = GsonBuilder().setLenient().create()
                return Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl("https://webeteer.com/")
                    .client(okHttpClient)
                    .build()
            }
            return retrofit
        }

        operator fun invoke(): MovieRetofitClient {
            val interceptor = HttpLoggingInterceptor()
            interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
            val okHttpClient: OkHttpClient = OkHttpClient.Builder()
                .readTimeout(60 * 5, TimeUnit.SECONDS)
                .connectTimeout(60 * 5, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build()
            val gson = GsonBuilder().setLenient().create()
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("https://webeteer.com/")
                .client(okHttpClient)
                .build()
                .create(MovieRetofitClient::class.java)
        }
    }


    @GET("dummy.json")
    fun fetchMovieList(): Call<Movies>
}