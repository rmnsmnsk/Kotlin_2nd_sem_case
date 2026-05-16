package model

import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val id: Int,
    val name: String,
    val color: Int,
    val wins: Int = 0,
    val losses: Int = 0,
    val gamesPlayed: Int = 0
) {
    fun getCellValue(): model.CellValue = when (color) {
        1 -> model.CellValue.BLACK
        2 -> model.CellValue.WHITE
        else -> throw IllegalArgumentException("Invalid color: $color")
    }

    fun withStats(wins: Int = this.wins, losses: Int = this.losses, gamesPlayed: Int = this.gamesPlayed): Player =
        copy(wins = wins, losses = losses, gamesPlayed = gamesPlayed)
}