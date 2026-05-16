package model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BoardTest {

    @Test
    fun `board size is 8`() {
        val cells = Array(8) { Array(8) { Cell(CellValue.EMPTY) } }
        val board = Board(cells)
        assertEquals(8, board.size)
    }

    @Test
    fun `get returns cell at coordinates`() {
        val cells = Array(8) { Array(8) { Cell(CellValue.EMPTY) } }
        cells[3][3] = Cell(CellValue.BLACK)
        val board = Board(cells)

        assertEquals(CellValue.BLACK, board.get(3, 3).value)
        assertEquals(CellValue.EMPTY, board.get(0, 0).value)
    }

    @Test
    fun `set returns new board with updated cell`() {
        val cells = Array(8) { Array(8) { Cell(CellValue.EMPTY) } }
        val original = Board(cells)
        val updated = original.set(2, 2, Cell(CellValue.WHITE))

        assertEquals(CellValue.EMPTY, original.get(2, 2).value)
        assertEquals(CellValue.WHITE, updated.get(2, 2).value)
    }

    @Test
    fun `count returns correct number for black`() {
        val cells = Array(8) { Array(8) { Cell(CellValue.EMPTY) } }
        cells[0][0] = Cell(CellValue.BLACK)
        cells[0][1] = Cell(CellValue.BLACK)
        cells[1][0] = Cell(CellValue.WHITE)
        val board = Board(cells)

        assertEquals(2, board.count(1))
    }

    @Test
    fun `count returns correct number for white`() {
        val cells = Array(8) { Array(8) { Cell(CellValue.EMPTY) } }
        cells[0][0] = Cell(CellValue.BLACK)
        cells[0][1] = Cell(CellValue.WHITE)
        cells[1][0] = Cell(CellValue.WHITE)
        val board = Board(cells)

        assertEquals(2, board.count(2))
    }

    @Test
    fun `isFull returns false when empty cells exist`() {
        val cells = Array(8) { Array(8) { Cell(CellValue.EMPTY) } }
        val board = Board(cells)
        assertFalse(board.isFull())
    }

    @Test
    fun `isFull returns true when all cells filled`() {
        val cells = Array(8) { Array(8) { Cell(CellValue.BLACK) } }
        val board = Board(cells)
        assertTrue(board.isFull())
    }
}