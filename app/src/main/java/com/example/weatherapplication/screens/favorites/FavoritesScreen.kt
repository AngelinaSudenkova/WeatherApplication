package com.example.weatherapplication.screens.favorites

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.weatherapplication.model.Favorite
import com.example.weatherapplication.navigation.WeatherScreens
import com.example.weatherapplication.utils.Constants
import com.example.weatherapplication.utils.formatDate
import com.example.weatherapplication.utils.formatDecimals
import com.example.weatherapplication.widgets.WeatherAppBar


@Composable
fun FavoritesScreen(
    navController: NavController,
    favoriteViewModel: FavoritesViewModel = hiltViewModel()
) {

    val listOfFav = favoriteViewModel.favList.collectAsState().value

    Scaffold(topBar = {
        WeatherAppBar(
            title = "Favorites",
            navController = navController,
            isMainScreen = false
        ) {
            navController.popBackStack()
        }
    }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            items(listOfFav) { favorite ->
                FavoriteRow(favorite, navController)
            }
        }
    }
}

@Composable
fun FavoriteRow(favoriteItem: Favorite,
                navController: NavController,
                favoriteViewModel: FavoritesViewModel = hiltViewModel()) {

    Surface(
        modifier = Modifier.fillMaxWidth().padding(16.dp).clickable(){
            navController.navigate(WeatherScreens.MainScreen.name + "/${favoriteItem.city}")
        },
        shape = CircleShape.copy(CornerSize(16.dp)),
        color = Color(0xFFFFC400).copy(alpha = 0.2f)
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = favoriteItem.city)

            Surface(
                shape = CircleShape,
                color = Color(0xFFFFC400)
            ) {
                Text(text = favoriteItem.country)
            }

            IconButton(onClick = {favoriteViewModel.deleteFavorite(favoriteItem)}) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Icon")
            }

        }
    }
}

