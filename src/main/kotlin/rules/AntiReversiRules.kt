package rules

import model.Board
import model.Player

class AntiReversiRules : ClassicRules() {

    override fun getWinner(board: Board): Int {
        val blackCount = board.count(1)
        val whiteCount = board.count(2)

        return when {
            blackCount < whiteCount -> 1
            whiteCount < blackCount -> 2
            else -> 0
        }
    }
}