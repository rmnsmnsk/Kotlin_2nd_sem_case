package rules

import model.Board
import model.Cell
import model.CellValue
import kotlin.test.Test
import kotlin.test.assertEquals

class AntiReversiRulesTest {

    private val rules = AntiReversiRules()

    @Test
    fun `getWinner returns black when black has fewer pieces`() {
        val cells = Array(8) { Array(8) { Cell(CellValue.EMPTY) } }
        for (i in 0..9) cells[i / 8][i % 8] = Cell(CellValue.BLACK)
        for (i in 10..29) cells[i / 8][i % 8] = Cell(CellValue.WHITE)
        val board = Board(cells)

        assertEquals(1, rules.getWinner(board))
    }

    @Test
    fun `getWinner returns white when white has fewer pieces`() {
        val cells = Array(8) { Array(8) { Cell(CellValue.EMPTY) } }
        for (i in 0..29) cells[i / 8][i % 8] = Cell(CellValue.BLACK)
        for (i in 30..39) cells[i / 8][i % 8] = Cell(CellValue.WHITE)
        val board = Board(cells)

        assertEquals(2, rules.getWinner(board))
    }

    @Test
    fun `getWinner returns 0 for equal counts`() {
        val cells = Array(8) { Array(8) { Cell(CellValue.EMPTY) } }
        for (i in 0..15) cells[i / 8][i % 8] = Cell(CellValue.BLACK)
        for (i in 16..31) cells[i / 8][i % 8] = Cell(CellValue.WHITE)
        val board = Board(cells)

        assertEquals(0, rules.getWinner(board))
    }
}