package com.example.couchpotato

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.google.accompanist.navigation.animation.AnimatedNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppScreen()
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppScreen() {
    val navController = rememberNavController()
    var selectedBottomTab by remember { mutableStateOf(0) }

    AnimatedNavHost(
        navController = navController,
        startDestination = "home",
        enterTransition = {
            slideInVertically(
                initialOffsetY = { it }, // Экран появляется снизу
                animationSpec = tween(500)
            ) + fadeIn(animationSpec = tween(500)) // Добавлено затухание для плавности
        },
        exitTransition = {
            slideOutVertically(
                targetOffsetY = { -it }, // Экран уходит вверх
                animationSpec = tween(500)
            ) + fadeOut(animationSpec = tween(500))
        },
        popEnterTransition = {
            slideInVertically(
                initialOffsetY = { -it }, // Возвращение сверху вниз
                animationSpec = tween(500)
            ) + fadeIn(animationSpec = tween(500))
        },
        popExitTransition = {
            slideOutVertically(
                targetOffsetY = { it }, // Закрытие экрана вниз
                animationSpec = tween(500)
            ) + fadeOut(animationSpec = tween(500))
        }
    ) {
        composable("home") {
            HomeScreen(
                navController = navController,
                selectedBottomTab = selectedBottomTab,
                onTabChange = { selectedBottomTab = it }
            )
        }
        composable(
            route = "details/{movieName}",
            arguments = listOf(navArgument("movieName") { defaultValue = "Unknown" })
        ) { backStackEntry ->
            val movieName = backStackEntry.arguments?.getString("movieName") ?: "Unknown"
            val movie = movieList.find { it.name == movieName }  // Найдем объект фильма по имени
            if (movie != null) {
                MovieDetailsScreen(movie = movie, navController = navController)  // Передаем весь объект фильма
            }
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
                    movies = movieList,
                    onMovieClick = { movie ->
                        navController.navigate("details/${movie.name}")
                    }
                )
                "Shows" -> ShowScreen(
                    shows = showList,
                    onShowClick = { show ->
                        navController.navigate("details/${show.name}")
                    }
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
fun MovieScreen(movies: List<Movie>, onMovieClick: (Movie) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(movies) { movie ->
            MovieCard(
                movieName = movie.name,
                posterUrl = movie.posterUrl,
                onClick = { onMovieClick(movie) }
            )
        }
    }
}


@Composable
fun ShowScreen(shows: List<Show>, onShowClick: (Show) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(shows) { show ->
            MovieCard(
                movieName = show.name,
                posterUrl = show.posterUrl,
                onClick = { onShowClick(show) }
            )
        }
    }
}


@Composable
fun MovieCard(movieName: String, posterUrl: String, onClick: () -> Unit) {
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
            AsyncImage(
                model = posterUrl,
                contentDescription = movieName,
                modifier = Modifier
                    .width(cardHeight)
                    .height(cardHeight),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.ic_launcher_background),
                error = painterResource(R.drawable.ic_launcher_foreground)
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
fun MovieDetailsScreen(movie: Movie, navController: NavController) {
    val posterSize = 200.dp
    var rating by remember { mutableStateOf(movie.rating) }

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
            AsyncImage(
                model = movie.posterUrl,
                contentDescription = movie.name,
                modifier = Modifier
                    .size(posterSize)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.ic_launcher_background),
                error = painterResource(R.drawable.ic_launcher_foreground)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = movie.name, fontSize = 24.sp, color = Color.Black)
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
                text = movie.description,
                fontSize = 16.sp,
                color = Color.Gray,
                textAlign = TextAlign.Justify,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
