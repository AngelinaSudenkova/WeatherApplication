package com.example.weatherapplication.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.weatherapplication.R
import com.example.weatherapplication.model.Weather
import com.example.weatherapplication.model.WeatherItem
import com.example.weatherapplication.navigation.WeatherScreens
import com.example.weatherapplication.utils.Constants
import com.example.weatherapplication.utils.formatDate
import com.example.weatherapplication.utils.formatDateTime
import com.example.weatherapplication.utils.formatDecimals
import com.example.weatherapplication.widgets.WeatherAppBar


@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel(),
    city: String?
) {

    LaunchedEffect(key1 = navController.currentBackStackEntry) {
        viewModel.loadWeather(city!!)
    }

    val weatherData = viewModel.data

    if (weatherData.value.loading == true) {
        CircularProgressIndicator()
    } else if (weatherData.value.data != null) {
        MainScaffold(weatherData.value.data!!, navController)
    }
}


@Composable
fun MainScaffold(weather: Weather, navController: NavController) {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            WeatherAppBar(
                title = weather.city.name + ", ${weather.city.country}",
                navController = navController,
                elevation = 4.dp,
                isMainScreen = true,
                onAddActionClicked = { navController.navigate(WeatherScreens.SearchScreen.name) }
            )
        }) {
        Column(modifier = Modifier.fillMaxSize()) {
            MainContent(modifier = Modifier.padding(it), data = weather)
            HumidityWindRow(weather = weather)
            HorizontalDivider()
            SunsetSunriseRow(weather = weather)
            LazyColumn(modifier = Modifier.fillMaxSize()){
                items(weather.list.size){ index ->
                    WeatherDetailRow(weather.list[index])
                }
            }
        }
    }
}

@Composable
fun WeatherDetailRow(weatherItem: WeatherItem) {
        Surface(modifier = Modifier.fillMaxWidth().padding(8.dp),
            shape = CircleShape.copy(CornerSize(16.dp)),
            color = Color(0xFFFFC400).copy(alpha = 0.2f)) {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = formatDate(weatherItem.dt).split(",")[0])
                Image(
                    painter = rememberAsyncImagePainter(Constants.IMG_URL + weatherItem.weather.first().icon + ".png"),
                    contentDescription = "Weather icon",
                    modifier = Modifier.size(80.dp)
                )

                Surface(
                    shape = CircleShape,
                    color = Color(0xFFFFC400)
                ) {
                    Text(text = weatherItem.weather.first().description)
                }

                Text(text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color.Black.copy(alpha = 0.7f),
                            fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold
                        )
                    ) {
                        Text(text = formatDecimals(weatherItem.temp.max) + "\u2103")
                    }
                })
                Text(text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color.Blue.copy(alpha = 0.7f),
                            fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold
                        )
                    ) {
                        Text(text = formatDecimals(weatherItem.temp.min) + "\u2103")
                    }
                })
            }
        }
}

@Composable
fun SunsetSunriseRow(weather: Weather) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Image(
                painter = painterResource(R.drawable.sunrise),
                contentDescription = "Sunrise icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = formatDateTime(weather.list.first().sunrise),
                style = MaterialTheme.typography.labelMedium
            )
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Image(
                painter = painterResource(R.drawable.sunset),
                contentDescription = "Pressure icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = formatDateTime(weather.list.first().sunset),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Composable
fun HumidityWindRow(weather: Weather) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        Row(modifier = Modifier.padding(4.dp)) {
            Image(
                painter = painterResource(R.drawable.humidity),
                contentDescription = "Humidity icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${weather.list.first().humidity}%",
                style = MaterialTheme.typography.labelMedium
            )
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Image(
                painter = painterResource(R.drawable.pressure),
                contentDescription = "Pressure icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${weather.list.first().pressure} psi",
                style = MaterialTheme.typography.labelMedium
            )
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Image(
                painter = painterResource(R.drawable.wind),
                contentDescription = "Wind icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${formatDecimals(weather.list.first().speed)} mph",
                style = MaterialTheme.typography.labelMedium,
            )
        }

    }
}

@Composable
fun MainContent(modifier: Modifier, data: Weather) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = formatDate(data.list.first().dt),
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(6.dp)
            )
            Surface(
                modifier = Modifier
                    .padding(4.dp)
                    .size(200.dp),
                shape = CircleShape,
                color = Color(0xFFFFC400)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(Constants.IMG_URL + data.list.first().weather.first().icon + ".png"),
                        contentDescription = "Weather icon",
                        modifier = Modifier.size(80.dp)
                    )
                    Text(
                        text = formatDecimals(data.list.first().temp.day) + "\u2103",

                        style = MaterialTheme.typography.headlineLarge
                    )
                    Text(text = data.list.first().weather.first().description)
                }
            }
        }

    }
}
