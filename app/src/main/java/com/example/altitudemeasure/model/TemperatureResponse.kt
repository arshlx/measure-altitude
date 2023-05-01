package com.example.altitudemeasure.model

import com.google.gson.annotations.SerializedName


data class TemperatureResponse(
    @SerializedName("data") var data: Data,
    @SerializedName("location") var location: Location
)