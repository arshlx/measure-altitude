package com.example.altitudemeasure.model

import com.google.gson.annotations.SerializedName


data class Location (

  @SerializedName("lat"  ) var latitude  : Double? = null,
  @SerializedName("lon"  ) var lonlongitude  : Double? = null,
  @SerializedName("name" ) var cityName : String? = null,
  @SerializedName("type" ) var type : String? = null

)