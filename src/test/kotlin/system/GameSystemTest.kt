package system

import model.Game
import model.Player
import model.Result
import repository.MatchRepositoryImpl
import repository.PlayerRepositoryImpl
import repository.GameRecord
import rules.ClassicRules
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class GameSystemTest {

    @Test
    fun `complete game scenario`() {
        val playerRepo = PlayerRepositoryImpl()
        val matchRepo = MatchRepositoryImpl()

        val player1 = Player(1, "Alice", 1)
        val player2 = Player(2, "Bob", 2)
        playerRepo.save(player1)
        playerRepo.save(player2)

        val game = Game(1, ClassicRules(), listOf(player1, player2))

        assertNotNull(playerRepo.findById(1))
        assertNotNull(playerRepo.findById(2))

        game.makeMove(1, 2, 3)

        val record = GameRecord(
            id = 1,
            players = listOf(1, 2),
            winnerId = game.winner?.getId(),
            moves = "",
            blackScore = game.blackScore,
            whiteScore = game.whiteScore
        )
        matchRepo.save(record)

        assertEquals(1, matchRepo.findAll().size)
        assertTrue(game.blackScore >= 0)
    }

    @Test
    fun `wrong player move should be invalid`() {
        val player1 = Player(1, "Alice", 1)
        val player2 = Player(2, "Bob", 2)
        val game = Game(1, ClassicRules(), listOf(player1, player2))

        val result = game.makeMove(2, 0, 0)
        assertEquals(Result.INVALID, result)
        assertNull(game.winner)
    }
}