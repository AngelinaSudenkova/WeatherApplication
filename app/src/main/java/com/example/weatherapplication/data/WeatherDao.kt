package com.example.weatherapplication.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.weatherapplication.model.Favorite
import com.example.weatherapplication.model.Unit
import kotlinx.coroutines.flow.Flow


@Dao
interface WeatherDao {
    @Query("SELECT * from fav_tbl")
    fun getFavorites() : Flow<List<Favorite>>

    @Query("SELECT * from fav_tbl where city =:city")
    fun getFavoriteById(city: String): Favorite

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavorite(favorite: Favorite)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavorite(favorite: Favorite)

    @Delete()
    suspend fun deleteFavorite(favorite: Favorite)

    @Query("DELETE from fav_tbl")
    suspend fun deleteAllFavorites()

    @Query("SELECT EXISTS(SELECT * from fav_tbl where city =:city)")
    suspend fun isFavorite(city: String): Boolean

    @Query("SELECT * from units_tbl")
    fun getUnits() : Flow<List<Unit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUnits(unit: Unit)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUnits(unit: Unit)

    @Delete()
    suspend fun deleteUnits(unit: Unit)



}