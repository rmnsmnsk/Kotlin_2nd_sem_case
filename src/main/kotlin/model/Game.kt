package model

import rules.Ruleset

class Game(
    private val id: Int,
    private val rules: Ruleset,
    private val players: List<Player>
) {
    private var board: Board = rules.createBoard()
    private var currentPlayer: Player = players[0]

    var winner: Player? = null
    var blackScore: Int = 0
    var whiteScore: Int = 0

    fun nextPlayer() {
        val index = players.indexOf(currentPlayer)
        if (index == -1) {
            throw IllegalStateException("Current player not found")
        }
        currentPlayer = players[(index + 1) % players.size]
    }

    fun makeMove(playerNumber: Int, x: Int, y: Int): Result {
        if (playerNumber != currentPlayer.getId()) {
            return Result.INVALID
        }

        if (!rules.isValidMove(board, x, y, currentPlayer.getColor())) {
            return Result.INVALID
        }

        board = rules.applyMove(board, x, y, currentPlayer.getColor())

        blackScore = board.count(1)
        whiteScore = board.count(2)

        if (rules.isGameOver(board)) {
            val winnerColor = rules.getWinner(board)
            winner = players.find { it.getColor() == winnerColor }
            return Result.GAME_OVER
        }

        nextPlayer()
        return Result.VALID
    }
}