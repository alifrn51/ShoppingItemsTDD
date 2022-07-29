package com.example.shoppinglistingtdd.data.remote

import com.example.shoppinglistingtdd.BuildConfig
import com.example.shoppinglistingtdd.data.remote.responses.ImageResponse
import com.example.shoppinglistingtdd.data.remote.responses.ImageResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApi {

    @GET("/api/")
    suspend fun searchForImage(
        @Query("q") searchQuery: String,
        @Query("key") apiKey: String = BuildConfig.API_KEY
    ): Response<ImageResponse>

}