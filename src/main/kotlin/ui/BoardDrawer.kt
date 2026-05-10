package ui

import model.Board

interface BoardRenderer {
    fun render(board: Board)
}