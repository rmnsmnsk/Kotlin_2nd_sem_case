package rules

import model.Board

interface Ruleset {
    fun createBoard(): Board
    fun isValidMove(board: Board, x: Int, y: Int, playerColor: Int): Boolean
    fun applyMove(board: Board, x: Int, y: Int, playerColor: Int): Board
    fun isGameOver(board: Board): Boolean
    fun getWinner(board: Board): Int
}