package model

data class Cell(
    internal val value: CellValue = CellValue.EMPTY,
    internal val playerId: Int? = null
) {
    fun isEmpty(): Boolean {
        return value == CellValue.EMPTY
    }

    fun belongsTo(playerId: Int): Boolean {
        return this.playerId == playerId
    }
}