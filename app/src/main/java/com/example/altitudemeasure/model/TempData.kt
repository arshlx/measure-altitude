package com.example.altitudemeasure.model

import com.google.gson.annotations.SerializedName

data class TempData(
    @SerializedName("time") val time: String,
    @SerializedName("values") val tempInfo: TempInfo
)