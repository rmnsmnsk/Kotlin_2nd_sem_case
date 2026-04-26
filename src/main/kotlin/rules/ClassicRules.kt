package rules

import model.Board
import model.Cell
import model.CellValue
import model.Player

class ClassicRules : Ruleset {
    override fun createBoard(): Board {
        val board = Board()
        board.set(3, 3, Cell(CellValue.WHITE, null))
        board.set(4, 4, Cell(CellValue.WHITE, null))
        board.set(3, 4, Cell(CellValue.BLACK, null))
        board.set(4, 3, Cell(CellValue.BLACK, null))
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
    //заглушки
    override fun applyMove(board: Board, x: Int, y: Int, playerColor: Int): Board {
        return board
    }

    override fun isGameOver(board: Board): Boolean {
        return false
    }

    override fun getWinner(board: Board): Int {
        return 0
    }

}