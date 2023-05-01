package com.example.altitudemeasure

import com.example.altitudemeasure.model.TempInfo

class Repository {
    private val remote = RetrofitService.getClient().create(APIRemote::class.java)
    suspend fun getTemperature(coordinates: String): Pair<String, TempInfo?> {
        var tempInfo:TempInfo
        try {
            val response = remote.getTemperature(coordinates)
            response.let {
                return Pair(TaskStatus.SUCCESS, response.body()!!.data.tempInfo)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            return Pair(TaskStatus.FAILURE, null)
        }
    }
}