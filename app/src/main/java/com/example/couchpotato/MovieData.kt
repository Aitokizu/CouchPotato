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
        posterUrl = "https://upload.wikimedia.org/wikipedia/en/7/7d/Inception_ver3.jpg",
        rating = 4
    ),
    Movie(
        name = "Avatar",
        description = "A paraplegic Marine is sent to the moon Pandora on a unique mission.",
        posterUrl = "https://upload.wikimedia.org/wikipedia/en/b/b0/Avatar-Teaser-Poster.jpg",
        rating = 5
    ),
    Movie(
        name = "Interstellar",
        description = "A team of explorers travel through a wormhole in space in an attempt to save humanity.",
        posterUrl = "https://upload.wikimedia.org/wikipedia/en/b/bc/Interstellar_film_poster.jpg",
        rating = 3
    )
)