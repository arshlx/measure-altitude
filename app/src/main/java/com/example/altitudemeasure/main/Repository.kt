package com.example.altitudemeasure.main

import com.example.altitudemeasure.model.TempInfo
import com.example.altitudemeasure.objects.Constants
import com.example.altitudemeasure.objects.TaskStatus
import com.example.altitudemeasure.services.APIRemote
import com.example.altitudemeasure.services.RetrofitService

class Repository {
    private val remote = RetrofitService.getClient().create(APIRemote::class.java)
    suspend fun getTemperature(coordinates: String): Pair<String, TempInfo?> {
        try {
            val response = remote.getTemperature(coordinates, Constants.unitType, Constants.apiKey)
            response.let {
                return Pair(TaskStatus.SUCCESS, response.body()!!.data.tempInfo)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            return Pair(TaskStatus.FAILURE, null)
        }
    }
}