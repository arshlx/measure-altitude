package com.example.altitudemeasure

import com.google.gson.annotations.SerializedName

data class GeoResponse(@SerializedName("results") val geoInfo: List<GeoInfo>)
