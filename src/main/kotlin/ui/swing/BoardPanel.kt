package ui.swing

import model.Board
import model.CellValue
import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JPanel

class BoardPanel(
    private var board: Board,
    private val onCellClick: (Int, Int) -> Unit
) : JPanel() {

    private val cellSize = 60
    private val padding = 10

    init {
        preferredSize = Dimension(
            board.size * cellSize + padding * 2,
            board.size * cellSize + padding * 2
        )

        addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                val x = (e.x - padding) / cellSize
                val y = (e.y - padding) / cellSize
                if (x in 0 until board.size && y in 0 until board.size) {
                    onCellClick(x, y)
                }
            }
        })
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g2 = g as Graphics2D
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

        for (row in 0 until board.size) {
            for (col in 0 until board.size) {
                val x = padding + col * cellSize
                val y = padding + row * cellSize

                g2.color = Color(46, 125, 50)
                g2.fillRoundRect(x, y, cellSize - 2, cellSize - 2, 5, 5)
                g2.color = Color(27, 94, 32)
                g2.drawRoundRect(x, y, cellSize - 2, cellSize - 2, 5, 5)

                val cell = board.get(col, row)
                if (cell.value != CellValue.EMPTY) {
                    val cx = x + cellSize / 2
                    val cy = y + cellSize / 2
                    val radius = cellSize / 2 - 8

                    g2.color = if (cell.value == CellValue.BLACK) Color.BLACK else Color.WHITE
                    g2.fillOval(cx - radius, cy - radius, radius * 2, radius * 2)
                    g2.color = Color.GRAY
                    g2.drawOval(cx - radius, cy - radius, radius * 2, radius * 2)
                }
            }
        }
    }

    fun updateBoard(newBoard: Board) {
        board = newBoard
        repaint()
    }
}