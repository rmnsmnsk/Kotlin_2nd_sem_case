package rules

import model.Board
import model.Cell
import model.CellValue
import model.Player

open class ClassicRules : Ruleset {
    override fun createBoard(): Board {
        var board = Board()
        board = board.set(3, 3, Cell(CellValue.WHITE, null))
        board = board.set(4, 4, Cell(CellValue.WHITE, null))
        board = board.set(3, 4, Cell(CellValue.BLACK, null))
        board = board.set(4, 3, Cell(CellValue.BLACK, null))
        return board
    }

    override fun isValidMove(board: Board, x: Int, y: Int, playerColor: Int): Boolean {
        if (!board.get(x, y).isEmpty()) return false

        val myColor = if (playerColor == 1) CellValue.BLACK else CellValue.WHITE
        val opponentColor = if (playerColor == 1) CellValue.WHITE else CellValue.BLACK

        for (dx in -1..1) {
            for (dy in -1..1) {
                if (dx == 0 && dy == 0) continue

                if (checkLine(board, x, y, dx, dy, myColor, opponentColor)) {
                    return true
                }
            }
        }
        return false
    }

    fun checkLine(board: Board, startX: Int, startY: Int, dx: Int, dy: Int, myColor: CellValue, opponentColor: CellValue): Boolean {
        var x = startX + dx
        var y = startY + dy

        if (x < 0 || x >= board.size || y < 0 || y >= board.size) return false
        if (board.get(x, y).isEmpty()) return false
        if (board.get(x, y).value != opponentColor) return false

        x += dx
        y += dy

        while (x >= 0 && x < board.size && y >= 0 && y < board.size) {
            if (board.get(x, y).isEmpty()) return false
            if (board.get(x, y).value == myColor) return true
            x += dx
            y += dy
        }

        return false
    }

    override fun applyMove(board: Board, x: Int, y: Int, playerColor: Int): Board {
        var newBoard = board
        val myColor = if (playerColor == 1) CellValue.BLACK else CellValue.WHITE
        val opponentColor = if (playerColor == 1) CellValue.WHITE else CellValue.BLACK

        newBoard = newBoard.set(x, y, Cell(myColor, playerColor))

        for (dx in -1..1) {
            for (dy in -1..1) {
                if (dx == 0 && dy == 0) continue
                if (checkLine(board, x, y, dx, dy, myColor, opponentColor)) {
                    var nx = x + dx
                    var ny = y + dy
                    while (nx in 0 until board.size && ny in 0 until board.size &&
                        board.get(nx, ny).value == opponentColor) {
                        newBoard = newBoard.set(nx, ny, Cell(myColor, playerColor))
                        nx += dx
                        ny += dy
                    }
                }
            }
        }
        return newBoard
    }

    override fun isGameOver(board: Board): Boolean {
        if (board.isFull()) return true
        return !hasAnyMove(board, 1) && !hasAnyMove(board, 2)
    }

    override fun getWinner(board: Board): Int {
        val blackCount = board.count(1)
        val whiteCount = board.count(2)
        return when {
            blackCount > whiteCount -> 1
            whiteCount > blackCount -> 2
            else -> 0
        }
    }

    private fun hasAnyMove(board: Board, playerColor: Int): Boolean {
        for (x in 0 until board.size) {
            for (y in 0 until board.size) {
                if (isValidMove(board, x, y, playerColor)) return true
            }
        }
        return false
    }
}