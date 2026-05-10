package ui

import model.Board
import model.CellValue

class ConsoleBoardRenderer : BoardRenderer {

    override fun render(board: Board) {
        println()
        println("  " + (0..7).joinToString(" "))

        for (y in 0..7) {
            print("$y ")
            for (x in 0..7) {
                when (board.get(x, y).value) {
                    CellValue.EMPTY -> print(". ")
                    CellValue.BLACK -> print("● ")
                    CellValue.WHITE -> print("○ ")
                }
            }
            println()
        }
        println()
    }
}