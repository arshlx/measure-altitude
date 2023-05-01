package com.example.altitudemeasure

import com.example.altitudemeasure.model.TempData
import com.example.altitudemeasure.model.TemperatureResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface APIRemote {
    @Headers("Accept: application/json")
    @GET("/realtime?location={coordinates}&units=metric&apikey=GUpWzkZxI6e0vbfCS3lvu7FFj8lOlY9R")
    suspend fun getTemperature(@Path("coordinates") coordinates: String): Response<TemperatureResponse>
}