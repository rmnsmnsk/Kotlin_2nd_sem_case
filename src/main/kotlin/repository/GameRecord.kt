package repository

data class GameRecord(
    val id: Int,
    val players: List<Int>,
    val winnerId: Int?,
    val moves: String,
    val blackScore: Int,
    val whiteScore: Int,
    val date: String = java.time.LocalDateTime.now().toString()
)