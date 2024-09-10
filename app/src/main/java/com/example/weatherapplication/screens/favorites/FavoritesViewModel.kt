package com.example.weatherapplication.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapplication.model.Favorite
import com.example.weatherapplication.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: WeatherDbRepository
) : ViewModel() {

    private val _favList = MutableStateFlow<List<Favorite>>(emptyList())
    val favList = _favList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavorites().collect {
                _favList.value = it
            }
        }
    }

    fun insertFavorite(favorite: Favorite) =
        viewModelScope.launch {
            repository.addFavorite(favorite)
        }

    fun updateFavorite(favorite: Favorite) =
        viewModelScope.launch {
            repository.updateFavorite(favorite)
        }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAllFavorites()
    }

    fun deleteFavorite(favorite: Favorite) = viewModelScope.launch {
        repository.deleteFavorite(favorite)
    }

    suspend fun isFavorite(city: String) : Boolean = repository.isFavorite(city)

}