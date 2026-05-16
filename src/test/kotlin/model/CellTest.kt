package model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CellTest {

    @Test
    fun `cell creation with EMPTY value`() {
        val cell = Cell(CellValue.EMPTY)
        assertEquals(CellValue.EMPTY, cell.value)
    }

    @Test
    fun `cell creation with BLACK value`() {
        val cell = Cell(CellValue.BLACK)
        assertEquals(CellValue.BLACK, cell.value)
    }

    @Test
    fun `cell creation with WHITE value`() {
        val cell = Cell(CellValue.WHITE)
        assertEquals(CellValue.WHITE, cell.value)
    }

    @Test
    fun `isEmpty returns true for EMPTY cell`() {
        val cell = Cell(CellValue.EMPTY)
        assertTrue(cell.isEmpty())
    }

    @Test
    fun `isEmpty returns false for BLACK cell`() {
        val cell = Cell(CellValue.BLACK)
        assertFalse(cell.isEmpty())
    }

    @Test
    fun `belongsTo returns true for matching player`() {
        val cell = Cell(CellValue.BLACK)
        assertTrue(cell.belongsTo(1))
    }

    @Test
    fun `belongsTo returns false for non-matching player`() {
        val cell = Cell(CellValue.BLACK)
        assertFalse(cell.belongsTo(2))
    }

    @Test
    fun `belongsTo returns false for empty cell`() {
        val cell = Cell(CellValue.EMPTY)
        assertFalse(cell.belongsTo(1))
    }
}