package com.example.photosearching.api.service

import com.example.photosearching.BuildConfig
import com.example.photosearching.api.UnsplashResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashApiService {

    companion object{
        //Base Url
        const val BASE_URL = "https://api.unsplash.com/"
        //Api Key
        const val API_KEY = BuildConfig.UNSPLASH_ACCESS_KEY

    }

    @Headers("Accept-Version: v1","Authorization: Client-ID $API_KEY")
    @GET("/search/photos")
    suspend fun getSearchPhoto(
        @Query("query")
        query: String,
        @Query("page")
        page: Int,
        @Query("per_page")
        perPage: Int
    ): UnsplashResponse
}