package com.example.altitudemeasure.model

import com.google.gson.annotations.SerializedName


data class TempInfo(
    @SerializedName("cloudBase") var cloudBase: Double,
    @SerializedName("cloudCeiling") var cloudCeiling: Double,
    @SerializedName("cloudCover") var cloudCover: Int,
    @SerializedName("dewPoint") var dewPoint: Double,
    @SerializedName("freezingRainIntensity") var freezingRainIntensity: Int,
    @SerializedName("humidity") var humidity: Int,
    @SerializedName("precipitationProbability") var precipitationProbability: Int,
    @SerializedName("pressureSurfaceLevel") var pressureSurfaceLevel: Double,
    @SerializedName("rainIntensity") var rainIntensity: Int,
    @SerializedName("sleetIntensity") var sleetIntensity: Int,
    @SerializedName("snowIntensity") var snowIntensity: Int,
    @SerializedName("temperature") var temperature: Double,
    @SerializedName("temperatureApparent") var temperatureApparent: Double,
    @SerializedName("uvHealthConcern") var uvHealthConcern: Int,
    @SerializedName("uvIndex") var uvIndex: Int,
    @SerializedName("visibility") var visibility: Double,
    @SerializedName("weatherCode") var weatherCode: Int,
    @SerializedName("windDirection") var windDirection: Double,
    @SerializedName("windGust") var windGust: Double,
    @SerializedName("windSpeed") var windSpeed: Double
)