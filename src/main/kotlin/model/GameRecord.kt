package model

import kotlinx.serialization.Serializable

@Serializable
data class GameRecord(
    val id: Int,
    val playerIds: List<Int>,
    val winnerId: Int?,
    val moves: String,
    val blackScore: Int,
    val whiteScore: Int,
    val date: String
)