package com.example.weatherapplication.screens.main

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapplication.data.DataOrException
import com.example.weatherapplication.model.Weather
import com.example.weatherapplication.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {


    private val _data: MutableState<DataOrException<Weather, Boolean, Exception>> =
        mutableStateOf(DataOrException(null, true, Exception("e")))

    val data: MutableState<DataOrException<Weather, Boolean, Exception>> = _data


    suspend fun loadWeather(city: String) {
        viewModelScope.launch {
            if (city.isEmpty()) return@launch
            _data.value.loading = true
            _data.value = repository.getWeather(cityQuery = city)
        }.join()
        if (_data.value.data.toString().isNotEmpty()) _data.value.loading = false
        Log.d("ResponseApi", _data.value.data.toString())
    }

}