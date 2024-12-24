package com.example.couchpotato

data class Movie(
    val name: String,
    val description: String,
    val posterUrl: String,
    var rating: Int
)

val movieList = listOf(
    Movie(
        name = "Inception",
        description = "Dom Cobb (Leonardo DiCaprio) is a thief with the rare ability to enter people's dreams and steal their secrets from their subconscious. His skill has made him a hot commodity in the world of corporate espionage but has also cost him everything he loves. Cobb gets a chance at redemption when he is offered a seemingly impossible task: Plant an idea in someone's mind. If he succeeds, it will be the perfect crime, but a dangerous enemy anticipates Cobb's every move.",
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