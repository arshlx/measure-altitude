package com.example.altitudemeasure.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.altitudemeasure.model.TempInfo
import com.example.altitudemeasure.objects.TaskStatus
import kotlinx.coroutines.launch

class GeoViewModel : ViewModel() {
    private val repository = Repository()
    val altitudeStatus = MutableLiveData(TaskStatus.NONE)
    var tempInfo: TempInfo? = null
    var coordinates = ""

    fun getTemperature() {
        altitudeStatus.value = TaskStatus.LOADING
        viewModelScope.launch {
            val result = repository.getTemperature(coordinates)
            tempInfo = result.second
            altitudeStatus.value = result.first
        }
    }
}