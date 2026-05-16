package model

data class Cell(val value: CellValue) {
    fun isEmpty(): Boolean = value == CellValue.EMPTY

    fun belongsTo(playerId: Int): Boolean {
        return when {
            value == CellValue.BLACK && playerId == 1 -> true
            value == CellValue.WHITE && playerId == 2 -> true
            else -> false
        }
    }
}