package rules

import model.Board
import model.Cell
import model.CellValue

open class ClassicRules : Ruleset {

    override fun createBoard(): Board {
        val cells = Array(8) { Array(8) { Cell(CellValue.EMPTY) } }
        cells[3][3] = Cell(CellValue.WHITE)
        cells[3][4] = Cell(CellValue.BLACK)
        cells[4][3] = Cell(CellValue.BLACK)
        cells[4][4] = Cell(CellValue.WHITE)
        return Board(cells)
    }

    override fun isValidMove(board: Board, x: Int, y: Int, playerColor: Int): Boolean {
        if (x !in 0..7 || y !in 0..7) return false
        if (board.get(x, y).value != CellValue.EMPTY) return false

        val directions = listOf(
            -1 to -1, -1 to 0, -1 to 1,
            0 to -1,           0 to 1,
            1 to -1,  1 to 0,  1 to 1
        )

        val opponentValue = if (playerColor == 1) CellValue.WHITE else CellValue.BLACK
        val playerValue = if (playerColor == 1) CellValue.BLACK else CellValue.WHITE

        for ((dx, dy) in directions) {
            var nx = x + dx
            var ny = y + dy
            var foundOpponent = false

            while (nx in 0..7 && ny in 0..7) {
                val cellValue = board.get(nx, ny).value
                if (cellValue == CellValue.EMPTY) break
                if (cellValue == opponentValue) {
                    foundOpponent = true
                } else if (cellValue == playerValue && foundOpponent) {
                    return true
                } else {
                    break
                }
                nx += dx
                ny += dy
            }
        }
        return false
    }

    override fun applyMove(board: Board, x: Int, y: Int, playerColor: Int): Board {
        val playerValue = if (playerColor == 1) CellValue.BLACK else CellValue.WHITE
        var newBoard = board.set(x, y, Cell(playerValue))

        val directions = listOf(
            -1 to -1, -1 to 0, -1 to 1,
            0 to -1,           0 to 1,
            1 to -1,  1 to 0,  1 to 1
        )

        val opponentValue = if (playerColor == 1) CellValue.WHITE else CellValue.BLACK

        for ((dx, dy) in directions) {
            var nx = x + dx
            var ny = y + dy
            val toFlip = mutableListOf<Pair<Int, Int>>()

            while (nx in 0..7 && ny in 0..7) {
                val cellValue = board.get(nx, ny).value
                if (cellValue == CellValue.EMPTY) break
                if (cellValue == opponentValue) {
                    toFlip.add(nx to ny)
                } else if (cellValue == playerValue && toFlip.isNotEmpty()) {
                    for ((fx, fy) in toFlip) {
                        newBoard = newBoard.set(fx, fy, Cell(playerValue))
                    }
                    break
                } else {
                    break
                }
                nx += dx
                ny += dy
            }
        }
        return newBoard
    }

    override fun isGameOver(board: Board): Boolean {
        return board.isFull()
    }

    override fun getWinner(board: Board): Int {
        val black = board.count(1)
        val white = board.count(2)
        return when {
            black > white -> 1
            white > black -> 2
            else -> 0
        }
    }
}