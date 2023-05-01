package com.example.altitudemeasure

import com.example.altitudemeasure.model.TemperatureResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface APIRemote {
    @Headers("Accept: application/json")
    @GET("realtime")
    suspend fun getTemperature(
        @Query("location") coordinates: String,
        @Query("units") unitType: String,
        @Query("apikey") apiKey: String
    ): Response<TemperatureResponse>
}