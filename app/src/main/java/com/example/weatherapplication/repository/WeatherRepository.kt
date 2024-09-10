package com.example.weatherapplication.repository

import android.util.Log
import com.example.weatherapplication.data.DataOrException
import com.example.weatherapplication.model.Weather
import com.example.weatherapplication.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi) {
    suspend fun getWeather(cityQuery: String): DataOrException<Weather, Boolean, Exception> {
        val response = try {
            api.getWeather(query = cityQuery)
        } catch (e: Exception) {
            return DataOrException(e = e)
        }
        return DataOrException(data = response)
    }

}