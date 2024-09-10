package com.example.weatherapplication.repository

import com.example.weatherapplication.data.WeatherDao
import com.example.weatherapplication.model.Favorite
import javax.inject.Inject
import com.example.weatherapplication.model.Unit

class WeatherDbRepository @Inject constructor(val dao: WeatherDao) {
    fun getFavorites() = dao.getFavorites()
    fun getFavoriteByCity(city: String) = dao.getFavoriteById(city)
    suspend fun addFavorite(favorite: Favorite) = dao.saveFavorite(favorite)
    suspend fun updateFavorite(favorite: Favorite) = dao.updateFavorite(favorite)
    suspend fun deleteFavorite(favorite: Favorite) = dao.deleteFavorite(favorite)
    suspend fun deleteAllFavorites() = dao.deleteAllFavorites()
    suspend fun isFavorite(city: String) = dao.isFavorite(city)

    fun getUnits() = dao.getUnits()
    suspend fun addUnit(unit: Unit) = dao.saveUnits(unit)
    suspend fun updateUnit(unit: Unit) = dao.updateUnits(unit)
    suspend fun deleteUnit(unit: Unit) = dao.deleteUnits(unit)


}