package model

class Player(
    private val: Int
    private val name: String
    private val color: Int
){

    fun getColor() Int{
        return this.color
    }

    fun getCellValue(): CellValue:
        if (color == 1){
            return CellValue.BlACK
        }
        else if (color == 2){
            return CellValue.WHITE
        }
        else{
            throw IllegalArgumentException()
        }
}