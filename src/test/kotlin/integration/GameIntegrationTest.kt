package integration

import model.Game
import model.Player
import model.Result
import repository.MatchRepositoryImpl
import repository.PlayerRepositoryImpl
import repository.GameRecord
import rules.ClassicRules
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class GameIntegrationTest {

    @Test
    fun `save match after move`() {
        val playerRepo = PlayerRepositoryImpl()
        val matchRepo = MatchRepositoryImpl()

        val player1 = Player(1, "Alice", 1)
        val player2 = Player(2, "Bob", 2)
        playerRepo.save(player1)
        playerRepo.save(player2)

        val game = Game(1, ClassicRules(), listOf(player1, player2))
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
    }

    @Test
    fun `players from repository should exist`() {
        val playerRepo = PlayerRepositoryImpl()
        playerRepo.save(Player(1, "Alice", 1))
        playerRepo.save(Player(2, "Bob", 2))

        val p1 = playerRepo.findById(1)
        val p2 = playerRepo.findById(2)
        assertNotNull(p1)
        assertNotNull(p2)
    }

    @Test
    fun `wrong player should fail`() {
        val game = Game(1, ClassicRules(), listOf(Player(1, "A", 1), Player(2, "B", 2)))
        assertEquals(Result.INVALID, game.makeMove(2, 2, 3))
    }
}