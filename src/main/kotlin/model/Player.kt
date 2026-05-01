package model

class Player(
    internal val id: Int,
    private val name: String,
    private val color: Int
) {
    fun getId(): Int {
        return id
    }

    fun getColor(): Int {
        return color
    }

    fun getCellValue(): CellValue {
        if (color == 1) {
            return CellValue.BLACK
        } else if (color == 2) {
            return CellValue.WHITE
        } else {
            throw IllegalArgumentException("Invalid color: $color")
        }
    }

    fun getName(): String {
        return name
    }
}