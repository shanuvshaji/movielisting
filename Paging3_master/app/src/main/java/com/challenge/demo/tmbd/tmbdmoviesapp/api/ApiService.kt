package com.challenge.demo.tmbd.tmbdmoviesapp.api

import com.challenge.demo.tmbd.tmbdmoviesapp.data.model.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie/now_playing")
    suspend fun fetchNowPlayingMovies1(
        @Query("page") page: Int,
        @Query("language") language: String
    ): Response<MoviesResponse>

}