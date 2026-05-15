package model

data class Board(
    private val cells: Array<Array<Cell>>
) {
    val size: Int = cells.size

    fun get(x: Int, y: Int): Cell = cells[x][y]

    fun set(x: Int, y: Int, cell: Cell): Board {
        val newCells = Array(size) { i -> Array(size) { j -> cells[i][j] } }
        newCells[x][y] = cell
        return Board(newCells)
    }

    fun count(color: Int): Int {
        val targetValue = if (color == 1) CellValue.BLACK else CellValue.WHITE
        var count = 0
        for (row in cells) {
            for (cell in row) {
                if (cell.value == targetValue) count++
            }
        }
        return count
    }

    fun isFull(): Boolean {
        for (row in cells) {
            for (cell in row) {
                if (cell.value == CellValue.EMPTY) return false
            }
        }
        return true
    }
}