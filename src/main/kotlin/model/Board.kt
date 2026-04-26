package model

class Board(val size: Int = 8) {

    internal val cells: Array<Array<Cell>> = Array(size) {
        Array(size) { Cell() }
    }

    fun get(x: Int, y: Int): Cell {
        if (x < 0 || x >= size || y < 0 || y >= size) {
            throw IllegalArgumentException("Coordinates ($x, $y) out of bounds")
        }
        return cells[x][y]
    }

    fun set(x: Int, y: Int, cell: Cell): Board {
        if (x < 0 || x >= size || y < 0 || y >= size) {
            throw IllegalArgumentException("Coordinates ($x, $y) out of bounds")
        }
        val newBoard = this.clone()
        newBoard.cells[x][y] = cell
        return newBoard
    }

    fun count(playerColor: Int): Int {
        if (playerColor < 0 || playerColor > 2) {
            throw IllegalArgumentException()
        }
        var count = 0
        for (x in 0 until size) {
            for (y in 0 until size) {
                if (!cells[x][y].isEmpty() && cells[x][y].value.ordinal == playerColor) {
                    count += 1
                }
            }
        }
        return count
    }

    fun isFull(): Boolean {
        return count(0) == 0
    }

    private fun clone(): Board {
        val newBoard = Board(size)
        for (x in 0 until size) {
            for (y in 0 until size) {
                newBoard.cells[x][y] = this.cells[x][y]
            }
        }
        return newBoard
    }
}