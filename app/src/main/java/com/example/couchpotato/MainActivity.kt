package com.example.couchpotato

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.navigation.NavController
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
    var selectedBottomTab by remember { mutableStateOf(0) } // Для нижней панели навигации

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(navController = navController, selectedBottomTab, onTabChange = { selectedBottomTab = it })
        }
        composable("details/{movieName}") { backStackEntry ->
            val movieName = backStackEntry.arguments?.getString("movieName") ?: "Unknown"
            MovieDetailsScreen(movieName = movieName, navController = navController)
        }
    }
}

@Composable
fun HomeScreen(navController: NavController, selectedBottomTab: Int, onTabChange: (Int) -> Unit) {
    var selectedTab by remember { mutableStateOf("Movies") }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedIndex = selectedBottomTab,
                onItemSelected = onTabChange
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
                    movies = listOf("Inception", "Avatar", "Interstellar", "The Dark Knight", "Titanic"),
                    onMovieClick = { movieName ->
                        navController.navigate("details/$movieName")
                    }
                )
                "Shows" -> ShowScreen(
                    shows = listOf("Breaking Bad", "Game of Thrones", "Stranger Things", "The Crown", "The Office"),
                    onShowClick = { showName ->
                        navController.navigate("details/$showName")
                    }
                )
            }
        }
    }
}

@Composable
fun SearchScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A3DA6))
            .padding(8.dp)
    ) {

        TextField(
            value = "",
            onValueChange = {  },
            placeholder = {
                Text(
                    text = "Search",
                    color = Color(0xFFA7C9DE),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "How about these tags:",
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            listOf("Action", "Drama", "Comedy", "Sci-Fi", "Horror", "Romance").forEach { tag ->
                Text(
                    text = tag,
                    color = Color(0xFF0F9BF2),
                    fontSize = 14.sp,
                    modifier = Modifier
                        .clickable {
                            // Когда-нибудт здесь будет переход к страницам жанра
                        }
                        .padding(8.dp)
                )
            }
        }
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

        items.forEachIndexed { index, item ->
            BottomNavigationItem(
                selected = selectedIndex == index,
                onClick = { onItemSelected(index) },
                icon = {
                    Icon(
                        painter = painterResource(id = item.second),
                        contentDescription = item.first,
                        modifier = Modifier.size(29.dp),
                        tint = if (selectedIndex == index) Color(0xFFF26E23) else Color(0xFF0F9BF2) // Цвет иконки зависит от выбранной вкладки
                    )
                },
                alwaysShowLabel = false
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
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun MovieScreen(movies: List<String>, onMovieClick: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(movies.size) { index ->
            MovieCard(movieName = movies[index], onClick = { onMovieClick(movies[index]) })
        }
    }
}

@Composable
fun ShowScreen(shows: List<String>, onShowClick: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(shows.size) { index ->
            MovieCard(movieName = shows[index], onClick = { onShowClick(shows[index]) })
        }
    }
}

@Composable
fun MovieCard(movieName: String, onClick: () -> Unit) {
    val cardHeight = 100.dp
    var rating by remember { mutableStateOf(3) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(cardHeight)
            .clickable { onClick() },
        elevation = 8.dp
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = movieName,
                modifier = Modifier
                    .width(cardHeight)
                    .height(cardHeight),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                Text(text = movieName, fontSize = 20.sp, color = Color.Black)
                Text(
                    text = "S01 | E01 out of 18 episodes",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    repeat(5) { index ->
                        Icon(
                            painter = painterResource(id = if (index < rating) R.drawable.star_full else R.drawable.star),
                            contentDescription = "Star $index",
                            modifier = Modifier.size(24.dp),
                            tint = Color(0xFFF13A28)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MovieDetailsScreen(movieName: String, navController: NavController) {
    val posterSize = 200.dp
    var rating by remember { mutableStateOf(3) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                backgroundColor = Color(0xFF0A3DA6),
                contentColor = Color.White,
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = "Back",
                            modifier = Modifier.size(32.dp),
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = movieName,
                modifier = Modifier
                    .size(posterSize)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = movieName, fontSize = 24.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.Center) {
                repeat(5) { index ->
                    Icon(
                        painter = painterResource(id = if (index < rating) R.drawable.star_full else R.drawable.star),
                        contentDescription = "Star $index",
                        modifier = Modifier
                            .size(32.dp)
                            .clickable { rating = index + 1 },
                        tint = Color(0xFFF13A28)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Detailed description about $movieName.",
                fontSize = 16.sp,
                color = Color.Gray,
                textAlign = TextAlign.Justify,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}