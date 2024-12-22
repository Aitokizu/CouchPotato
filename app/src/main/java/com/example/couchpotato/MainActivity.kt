package com.example.couchpotato

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import com.example.couchpotato.ui.theme.CouchPotatoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppScreen()
        }
    }
}

@Composable
fun AppScreen() {
    var selectedTab by remember { mutableStateOf("Movies") }
    var selectedBottomTab by remember { mutableStateOf(0) } // Состояние для нижней навигационной панели

    Scaffold(
        modifier = Modifier.fillMaxSize(), // Убедитесь, что Scaffold занимает весь экран
        bottomBar = {
            BottomNavigationBar(
                selectedIndex = selectedBottomTab,
                onItemSelected = { selectedBottomTab = it }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0A3DA6))
                .padding(paddingValues)
        ) {
            TabSelector(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )

            when (selectedTab) {
                "Movies" -> MovieScreen(
                    movies = listOf("Inception", "Avatar", "Interstellar", "The Dark Knight", "Titanic")
                )
                "Shows" -> ShowScreen(
                    shows = listOf("Breaking Bad", "Game of Thrones", "Stranger Things", "The Crown", "The Office")
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(selectedIndex: Int, onItemSelected: (Int) -> Unit) {
    BottomNavigation(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp) // Устанавливаем фиксированную высоту для панели
            .background(Color.White), // Белый фон для панели
        backgroundColor = Color.White
    ) {
        val items = listOf(
            "Home" to R.drawable.home_notactive,
            "Search" to R.drawable.search_notactive,
            "Favorites" to R.drawable.favorites_notactive,
            "Profile" to R.drawable.profile_notactive
        )

        items.forEachIndexed { index, item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.second),
                        contentDescription = item.first
                    )
                },
                label = { BasicText(item.first) },
                selected = selectedIndex == index,
                onClick = { onItemSelected(index) },
                selectedContentColor = Color.Black, // Черный цвет для выбранной иконки
                unselectedContentColor = Color.Gray // Серый цвет для невыбранной иконки
            )
        }
    }
}

@Composable
fun TabSelector(selectedTab: String, onTabSelected: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        listOf("Movies", "Shows").forEach { tab ->
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (selectedTab == tab) Color(0xFF0F9BF2) else Color.Transparent)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clickable { onTabSelected(tab) }
            ) {
                Text(
                    text = tab,
                    color = if (selectedTab == tab) Color.White else Color.White.copy(alpha = 0.7f),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun MovieScreen(movies: List<String>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(movies.size) { index ->
            MovieCard(movieName = movies[index])
        }
    }
}

@Composable
fun ShowScreen(shows: List<String>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(shows.size) { index ->
            MovieCard(movieName = shows[index])
        }
    }
}

@Composable
fun MovieCard(movieName: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        elevation = 8.dp
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = movieName,
                fontSize = 20.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
    }
}