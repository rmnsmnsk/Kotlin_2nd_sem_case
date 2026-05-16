package ui

import model.Board
import model.CellValue

interface BoardRenderer {
    fun render(board: Board)
}

class ConsoleBoardRenderer : BoardRenderer {
    override fun render(board: Board) {
        println("  " + (0 until board.size).joinToString(" ") { it.toString().padEnd(3) })
        for (y in 0 until board.size) {
            print("${y.toString().padEnd(2)}")
            for (x in 0 until board.size) {
                val cell = board.get(x, y)
                val symbol = when (cell.value) {
                    CellValue.EMPTY -> "."
                    CellValue.BLACK -> "B"
                    CellValue.WHITE -> "W"
                }
                print("$symbol  ")
            }
            println()
        }
    }
}