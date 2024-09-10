package com.example.weatherapplication.network

import android.os.Build
import com.example.weatherapplication.BuildConfig
import com.example.weatherapplication.model.Weather
import com.example.weatherapplication.model.WeatherObject
import com.example.weatherapplication.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton


@Singleton
interface WeatherApi {
    @GET(value = " data/2.5/forecast/daily")
    suspend fun getWeather(
        @Query("q") query : String,
        @Query("units") units : String = "metric",
        @Query ("appid") appid : String  = BuildConfig.API_KEY
    ) : Weather

}