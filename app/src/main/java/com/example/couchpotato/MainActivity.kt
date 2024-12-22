package com.example.couchpotato

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

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
    val navController = rememberNavController()
    var selectedTab by remember { mutableStateOf("Movies") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(
                selectedIndex = when (navController.currentBackStackEntry?.destination?.route) {
                    "home" -> 0
                    "search" -> 1
                    "favorites" -> 2
                    "profile" -> 3
                    else -> 0
                },
                onItemSelected = { index ->
                    when (index) {
                        0 -> navController.navigate("home")
                        1 -> navController.navigate("search")
                        2 -> navController.navigate("favorites")
                        3 -> navController.navigate("profile")
                    }
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("home") {
                HomeScreen(
                    selectedTab = selectedTab,
                    onTabSelected = { selectedTab = it }
                )
            }
            composable("search") {
                SearchScreen()
            }
            composable("favorites") {
                FavoritesScreen()
            }
            composable("profile") {
                ProfileScreen()
            }
        }
    }
}

@Composable
fun HomeScreen(selectedTab: String, onTabSelected: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A3DA6))
    ) {
        TabSelector(selectedTab = selectedTab, onTabSelected = onTabSelected)

        when (selectedTab) {
            "Movies" -> MovieScreen(
                movies = listOf("Inception", "Avatar", "Interstellar", "The Dark Knight", "Titanic"),
                inProgressMovies = listOf("Inception", "Avatar"),
                notStartedMovies = listOf("Interstellar", "The Dark Knight", "Titanic")
            )
            "Shows" -> ShowScreen(
                shows = listOf("Breaking Bad", "Game of Thrones", "Stranger Things", "The Crown", "The Office")
            )
        }
    }
}

@Composable
fun SearchScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Search Screen",
            fontSize = 24.sp,
            color = Color.Black
        )
    }
}

@Composable
fun FavoritesScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Favorites Screen",
            fontSize = 24.sp,
            color = Color.Black
        )
    }
}

@Composable
fun ProfileScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Profile Screen",
            fontSize = 24.sp,
            color = Color.Black
        )
    }
}

@Composable
fun BottomNavigationBar(selectedIndex: Int, onItemSelected: (Int) -> Unit) {
    BottomNavigation(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .background(Color.White),
        backgroundColor = Color.White
    ) {
        val items = listOf(
            "Home" to R.drawable.home,
            "Search" to R.drawable.search,
            "Favorites" to R.drawable.fav,
            "Profile" to R.drawable.profile
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween, // Располагаем элементы на равном расстоянии
            verticalAlignment = Alignment.CenterVertically // Выравнивание по центру по вертикали
        ) {
            items.forEachIndexed { index, item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = item.second),
                            contentDescription = item.first,
                            modifier = Modifier.size(29.dp),
                            tint = if (selectedIndex == index) Color(0xFFF26E23) else Color(0xFF0F9BF2)
                        )
                    },
                    selected = selectedIndex == index,
                    onClick = { onItemSelected(index) },
                    selectedContentColor = Color(0xFFF26E23),
                    unselectedContentColor = Color(0xFF0F9BF2),
                    label = {}
                )
            }
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
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun MovieScreen(movies: List<String>, inProgressMovies: List<String>, notStartedMovies: List<String>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (inProgressMovies.isNotEmpty()) {
            item {
                Text(
                    text = "In progress",
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            items(inProgressMovies.size) { index ->
                MovieCard(movieName = inProgressMovies[index])
            }
        } else {
            item {
                Text(
                    text = "wow...such emptiness!",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                )
            }
        }

        if (notStartedMovies.isNotEmpty()) {
            item {
                Text(
                    text = "Not started yet",
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            items(notStartedMovies.size) { index ->
                MovieCard(movieName = notStartedMovies[index])
            }
        } else {
            item {
                Text(
                    text = "wow...such emptiness!",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                )
            }
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
    val cardHeight = 100.dp
    val posterSize = cardHeight
    var rating by remember { mutableStateOf(3) } // Изначальный рейтинг
    var showDialog by remember { mutableStateOf(false) } // Состояние для отображения диалога

    // oкно с подробной информацией
    if (showDialog) {
        MovieDetailDialog(
            movieName = movieName,
            onDismiss = { showDialog = false }
        )
    }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(cardHeight)
            .clickable { showDialog = true },
        elevation = 8.dp
    ) {
        Row(modifier = Modifier.fillMaxSize()) {Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = movieName,
            modifier = Modifier
                .width(posterSize)
                .height(posterSize),
            contentScale = ContentScale.Crop
        )


            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .fillMaxHeight()
                    .align(Alignment.Top)
                    .weight(1f)
            ) {

                Text(
                    text = movieName,
                    fontSize = 20.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )


                Text(
                    text = "S01 | E01 out of 18 episodes",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                )


                Row(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    repeat(5) { index ->
                        Icon(
                            painter = painterResource(id = if (index < rating) R.drawable.star_full else R.drawable.star),
                            contentDescription = "Star $index",
                            modifier = Modifier
                                .size(24.dp)
                                .clickable { rating = index + 1 }, // Установка рейтинга по клику
                            tint = Color(0xFFF13A28)
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun MovieDetailDialog(movieName: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Details for $movieName", fontSize = 20.sp, color = Color.Black)
        },
        text = {
            Column {
                Text(text = "Genre: Sci-Fi, Drama")
                Text(text = "Director: Christopher Nolan")
                Text(text = "Release Year: 2010")
                Text(text = "Duration: 148 minutes")
                Text(text = "Rating: 8.8/10")}
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close", color = Color(0xFF0F9BF2))
            }
        },
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(16.dp)
    )
}