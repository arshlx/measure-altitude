package com.example.altitudemeasure

class Repository {
    private val remote = RetrofitService.getClient().create(APIRemote::class.java)
    suspend fun getElevation(coordinates: String): Pair<String, Float> {
        var altitude = 0f
        try {
            val response = remote.getAltitude(coordinates)
            response.let {
                altitude = response.body()!!.geoInfo.first().elevation
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return Pair(TaskStatus.SUCCESS, altitude)
    }
}