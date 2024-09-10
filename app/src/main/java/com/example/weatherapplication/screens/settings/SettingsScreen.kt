package com.example.weatherapplication.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.weatherapplication.widgets.WeatherAppBar

@Composable
fun SettingsScreen(navController: NavController){
    Scaffold(topBar = {
        WeatherAppBar(
            title = "Settings",
            navController = navController,
            isMainScreen = false
        ){
            navController.popBackStack()
        }
    }
    ){
        Column(modifier = Modifier.padding(it)) {
            Text(text = "Settings Screen")
        }
    }
}