package com.example.weatherapplication.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherapplication.model.Favorite
import com.example.weatherapplication.navigation.WeatherScreens
import com.example.weatherapplication.screens.favorites.FavoritesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppBar(
    title: String = "Title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    favoritesViewModel: FavoritesViewModel = hiltViewModel<FavoritesViewModel>(),
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
) {
    Surface(
        shadowElevation = elevation
    ) {

        val showDialog = remember {
            mutableStateOf(false)
        }

        val isFavorite = remember {
            mutableStateOf(false)
        }

        LaunchedEffect(key1 = isFavorite.value) {
            isFavorite.value = favoritesViewModel.isFavorite(title.split(",")[0])
        }

        TopAppBar(
            title = { Text(text = title) },
            actions = {
                if (isMainScreen) {

                    IconButton(onClick = {
                        isFavorite.value = !isFavorite.value
                        val favorite = Favorite(
                            city = title.split(",")[0],
                            country = title.split(",")[1]
                        )
                        if (isFavorite.value) {
                            favoritesViewModel.insertFavorite(
                                favorite
                            )
                        } else {
                            favoritesViewModel.deleteFavorite(favorite)
                        }
                    }) {
                        Icon(
                            imageVector = if (isFavorite.value) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite icon",
                            tint = Color.Red.copy(alpha = 0.6f)
                        )
                    }

                    IconButton(onClick = {
                        navController.navigate(WeatherScreens.SearchScreen.name)
                    }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "search icon")
                    }
                    IconButton(onClick = {
                        showDialog.value = !showDialog.value
                    }
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "more icon"
                        )
                    }
                }
            },
            navigationIcon = {
                if (!isMainScreen) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "back icon"
                        )
                    }
                }
            },
            colors = TopAppBarColors(
                containerColor = Color.White,
                scrolledContainerColor = Color.White,
                titleContentColor = Color.Black,
                actionIconContentColor = Color.Black,
                navigationIconContentColor = Color.Black
            ),
            modifier = Modifier.fillMaxWidth()
        )

        val items = listOf("About", "Favorites", "Settings")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .offset(x = (-10).dp),
            contentAlignment = Alignment.TopEnd

        ) {
            DropdownMenu(
                expanded = showDialog.value,
                onDismissRequest = { showDialog.value = false },
                modifier = Modifier
                    .background(Color.White)
                    .width(160.dp)
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        onClick = {
                            navController.navigate(
                                when (item) {
                                    "About" -> WeatherScreens.AboutScreen.name
                                    "Favorites" -> WeatherScreens.FavoriteScreen.name
                                    else -> WeatherScreens.SettingsScreen.name
                                }
                            )
                        },
                        modifier = Modifier
                            .padding(8.dp),
                        text = {
                            Row {
                                Icon(
                                    imageVector = when (item) {
                                        "About" -> Icons.Default.Info
                                        "Favorites" -> Icons.Default.Favorite
                                        else -> Icons.Default.Settings
                                    },
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = item)
                            }
                        }
                    )
                }
            }
        }

    }
}

