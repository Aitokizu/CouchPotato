package com.example.couchpotato

data class Show(
    val name: String,
    val description: String,
    val posterUrl: String,
    val rating: Int
)

val showList = listOf(
    Show(
        name = "Breaking Bad",
        description = "A high school chemistry teacher turned methamphetamine manufacturer teams up with a former student.",
        posterUrl = "https://m.media-amazon.com/images/M/MV5BMzU5ZGYzNmQtMTdhYy00OGRiLTg0NmQtYjVjNzliZTg1ZGE4XkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg",
        rating = 5
    ),
    Show(
        name = "Game of Thrones",
        description = "Nine noble families fight for control over the mythical lands of Westeros.",
        posterUrl = "https://m.media-amazon.com/images/M/MV5BMTNhMDJmNmYtNDQ5OS00ODdlLWE0ZDAtZTgyYTIwNDY3OTU3XkEyXkFqcGc@._V1_.jpg",
        rating = 5
    ),
    Show(
        name = "The Office",
        description = "A mockumentary on a group of typical office workers, documenting their everyday lives.",
        posterUrl = "https://m.media-amazon.com/images/M/MV5BZjQwYzBlYzUtZjhhOS00ZDQ0LWE0NzAtYTk4MjgzZTNkZWEzXkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg",
        rating = 3
    )
)