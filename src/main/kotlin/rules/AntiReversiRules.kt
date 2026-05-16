package rules

import model.Board
import model.CellValue

class AntiReversiRules : ClassicRules() {

    override fun getWinner(board: Board): Int {
        val black = board.count(1)
        val white = board.count(2)
        return when {
            black < white -> 1
            white < black -> 2
            else -> 0
        }
    }
}