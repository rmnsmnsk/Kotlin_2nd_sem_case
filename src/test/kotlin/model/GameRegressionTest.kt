package model

import rules.ClassicRules
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class GameRegressionTest {

    @Test fun `initial board has 4 pieces`() {
        val players = listOf(Player(1, "A", 1), Player(2, "B", 2))
        val game = Game(1, ClassicRules(), players)
        assertEquals(2, game.board.count(1))
        assertEquals(2, game.board.count(2))
    }

    @Test fun `invalid move returns INVALID`() {
        val players = listOf(Player(1, "A", 1), Player(2, "B", 2))
        val game = Game(1, ClassicRules(), players)
        assertEquals(Result.INVALID, game.makeMove(1, 0, 0))
    }

    @Test fun `valid move changes board state`() {
        val players = listOf(Player(1, "A", 1), Player(2, "B", 2))
        val game = Game(1, ClassicRules(), players)

        val boardBefore = game.board
        val result = game.makeMove(1, 2, 3)

        assertEquals(Result.VALID, result)
        assertNotEquals(boardBefore, game.board)
        assertTrue(game.board.count(1) != 2 || game.board.count(2) != 2)
    }

    @Test fun `game not over after start`() {
        val players = listOf(Player(1, "A", 1), Player(2, "B", 2))
        val game = Game(1, ClassicRules(), players)
        assertTrue(!game.board.isFull())
    }
}