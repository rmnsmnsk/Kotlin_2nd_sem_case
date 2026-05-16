package model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class PlayerTest {

    @Test
    fun `player creation with valid data`() {
        val player = Player(1, "Alice", 1)
        assertEquals(1, player.id)
        assertEquals("Alice", player.name)
        assertEquals(1, player.color)
        assertEquals(0, player.wins)
        assertEquals(0, player.losses)
        assertEquals(0, player.gamesPlayed)
    }

    @Test
    fun `withStats returns updated copy`() {
        val player = Player(2, "Bob", 2)
        val updated = player.withStats(wins = 5, losses = 3, gamesPlayed = 8)

        assertEquals(2, updated.id)
        assertEquals(5, updated.wins)
        assertEquals(3, updated.losses)
        assertEquals(8, updated.gamesPlayed)
        assertEquals("Bob", updated.name)
    }

    @Test
    fun `getCellValue returns BLACK for color 1`() {
        val player = Player(1, "Black", 1)
        assertEquals(CellValue.BLACK, player.getCellValue())
    }

    @Test
    fun `getCellValue returns WHITE for color 2`() {
        val player = Player(2, "White", 2)
        assertEquals(CellValue.WHITE, player.getCellValue())
    }

    @Test
    fun `getCellValue throws for invalid color`() {
        val player = Player(3, "Invalid", 99)
        assertFailsWith<IllegalArgumentException> {
            player.getCellValue()
        }
    }
}