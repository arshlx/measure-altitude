package com.example.altitudemeasure

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface APIRemote {
    @Headers("Accept: application/json")
    @GET("lookup?locations={coordinates}")
    suspend fun getAltitude(@Path("coordinates") coordinates: String): Response<GeoResponse>
}