package model

class Cell(
    internal val value: CellValue = CellValue.EMPTY
    internal val playerId: Int?
){
    fun isEmpty(): Boolean{
        return value == CellValue.EMPTY
    }

    fun belongsTo(playerId: Int) Boolean{
        return this.playedId == playerId
    }

}