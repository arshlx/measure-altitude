package com.example.altitudemeasure.model

import com.google.gson.annotations.SerializedName


data class Data(
    @SerializedName("time") var time: String,
    @SerializedName("values") var tempInfo: TempInfo
)