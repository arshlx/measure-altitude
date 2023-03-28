package com.example.altitudemeasure

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class GeoViewModel: ViewModel() {
    private val repository = Repository()
    val altitudeStatus = MutableLiveData(TaskStatus.NONE)
    var elevation = 0f
    var coordinates = ""
    fun getElevation() {
        altitudeStatus.value = TaskStatus.LOADING
        viewModelScope.launch {
            val result = repository.getElevation(coordinates)
            elevation = result.second
            altitudeStatus.value = result.first
        }
    }
}