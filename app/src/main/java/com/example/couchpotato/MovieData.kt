package com.example.couchpotato

data class Movie(
    val name: String,
    val description: String,
    val posterUrl: String,
    val rating: Int
)

val movieList = listOf(
    Movie(
        name = "Inception",
        description = "A skilled thief is given a chance to erase his past crimes by planting an idea into a CEO's mind.",
        posterUrl = "https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_FMjpg_UX1000_.jpg",
        rating = 4
    ),
    Movie(
        name = "Some movie",
        description = "Something something",
        posterUrl = "tipa bolvanka",
        rating = 5
    ),
    Movie(
        name = "Bee Movie",
        description = "BEEES",
        posterUrl = "https://raisingchildren.net.au/__data/assets/image/0035/49895/bee-movie.jpg",
        rating = 3
    )
)