package rules

import model.Board
import model.Cell
import model.CellValue
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ClassicRulesTest {

    private val rules = ClassicRules()

    @Test
    fun `createBoard returns 8x8 board`() {
        val board = rules.createBoard()
        assertEquals(8, board.size)
    }

    @Test
    fun `createBoard has initial setup`() {
        val board = rules.createBoard()
        assertEquals(2, board.count(1))
        assertEquals(2, board.count(2))
        assertEquals(CellValue.WHITE, board.get(3, 3).value)
        assertEquals(CellValue.BLACK, board.get(3, 4).value)
        assertEquals(CellValue.BLACK, board.get(4, 3).value)
        assertEquals(CellValue.WHITE, board.get(4, 4).value)
    }

    @Test
    fun `isValidMove rejects out of bounds`() {
        val board = rules.createBoard()
        assertFalse(rules.isValidMove(board, -1, 0, 1))
        assertFalse(rules.isValidMove(board, 8, 0, 1))
        assertFalse(rules.isValidMove(board, 0, 8, 1))
        assertFalse(rules.isValidMove(board, 0, -1, 1))
    }

    @Test
    fun `isValidMove rejects occupied cell`() {
        val board = rules.createBoard()
        assertFalse(rules.isValidMove(board, 3, 3, 1))
        assertFalse(rules.isValidMove(board, 3, 4, 2))
    }

    @Test
    fun `isValidMove accepts valid move for black`() {
        val board = rules.createBoard()
        assertTrue(rules.isValidMove(board, 2, 3, 1))
        assertTrue(rules.isValidMove(board, 3, 2, 1))
        assertTrue(rules.isValidMove(board, 4, 5, 1))
        assertTrue(rules.isValidMove(board, 5, 4, 1))
    }

    @Test
    fun `isValidMove accepts valid move for white`() {
        val board = rules.createBoard()
        assertTrue(rules.isValidMove(board, 2, 4, 2))
        assertTrue(rules.isValidMove(board, 4, 2, 2))
        assertTrue(rules.isValidMove(board, 5, 3, 2))
        assertTrue(rules.isValidMove(board, 3, 5, 2))
    }

    @Test
    fun `applyMove places piece and flips opponents`() {
        val board = rules.createBoard()
        val newBoard = rules.applyMove(board, 2, 3, 1)

        assertEquals(CellValue.BLACK, newBoard.get(2, 3).value)
        assertEquals(CellValue.BLACK, newBoard.get(3, 3).value)
        assertEquals(CellValue.BLACK, newBoard.get(3, 4).value)
        assertEquals(CellValue.BLACK, newBoard.get(4, 3).value)
        assertEquals(CellValue.WHITE, newBoard.get(4, 4).value)
        assertEquals(4, newBoard.count(1))
        assertEquals(1, newBoard.count(2))
    }

    @Test
    fun `isGameOver returns false for non-full board`() {
        val board = rules.createBoard()
        assertFalse(rules.isGameOver(board))
    }

    @Test
    fun `isGameOver returns true for full board`() {
        val cells = Array(8) { Array(8) { Cell(CellValue.BLACK) } }
        val fullBoard = Board(cells)
        assertTrue(rules.isGameOver(fullBoard))
    }

    @Test
    fun `getWinner returns black when black has more`() {
        val cells = Array(8) { Array(8) { Cell(CellValue.EMPTY) } }
        for (i in 0..3) cells[i][0] = Cell(CellValue.BLACK)
        for (i in 0..1) cells[i][1] = Cell(CellValue.WHITE)
        val board = Board(cells)

        assertEquals(1, rules.getWinner(board))
    }

    @Test
    fun `getWinner returns white when white has more`() {
        val cells = Array(8) { Array(8) { Cell(CellValue.EMPTY) } }
        for (i in 0..1) cells[i][0] = Cell(CellValue.BLACK)
        for (i in 0..3) cells[i][1] = Cell(CellValue.WHITE)
        val board = Board(cells)

        assertEquals(2, rules.getWinner(board))
    }

    @Test
    fun `getWinner returns 0 for draw`() {
        val cells = Array(8) { Array(8) { Cell(CellValue.EMPTY) } }
        for (i in 0..15) cells[i / 8][i % 8] = Cell(CellValue.BLACK)
        for (i in 16..31) cells[i / 8][i % 8] = Cell(CellValue.WHITE)
        val board = Board(cells)

        assertEquals(0, rules.getWinner(board))
    }
}