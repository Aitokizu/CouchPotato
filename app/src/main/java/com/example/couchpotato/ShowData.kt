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
        posterUrl = "https://upload.wikimedia.org/wikipedia/en/6/61/Breaking_Bad_title_card.png",
        rating = 5
    ),
    Show(
        name = "Game of Thrones",
        description = "Nine noble families fight for control over the mythical lands of Westeros.",
        posterUrl = "https://upload.wikimedia.org/wikipedia/en/6/63/Game_of_Thrones_logo.png",
        rating = 5
    ),
    Show(
        name = "Stranger Things",
        description = "A group of kids in the 80s uncover a series of supernatural mysteries in their small town.",
        posterUrl = "https://upload.wikimedia.org/wikipedia/en/3/3f/Stranger_Things_logo.png",
        rating = 4
    ),
    Show(
        name = "The Crown",
        description = "The reign of Queen Elizabeth II is depicted in this historical drama.",
        posterUrl = "https://upload.wikimedia.org/wikipedia/en/3/3f/The_Crown_logo.jpg",
        rating = 4
    ),
    Show(
        name = "The Office",
        description = "A mockumentary on a group of typical office workers, documenting their everyday lives.",
        posterUrl = "https://upload.wikimedia.org/wikipedia/en/4/4f/The_Office_Logo.jpg",
        rating = 3
    )
)