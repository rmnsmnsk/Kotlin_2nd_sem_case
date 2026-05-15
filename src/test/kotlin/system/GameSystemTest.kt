package system

import model.Game
import model.Player
import repository.JsonPlayerRepository
import rules.ClassicRules
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class GameSystemTest {

    @TempDir lateinit var tmp: Path

    @Test fun `game creates and saves players`() {
        val playerRepo = JsonPlayerRepository(tmp.resolve("players.json"))

        val p1 = Player(1, "Alice", 1)
        val p2 = Player(2, "Bob", 2)
        playerRepo.save(p1)
        playerRepo.save(p2)

        val game = Game(1, ClassicRules(), listOf(p1, p2))

        assertNotNull(game)
        assertEquals(2, game.getPlayers().size)
        assertEquals(2, playerRepo.findAll().size)
    }
}