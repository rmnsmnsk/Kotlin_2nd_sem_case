package repository

import model.Player
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class JsonPlayerRepositoryTest {
    @TempDir lateinit var tmp: Path

    @Test fun `save and find player`() {
        val repo = JsonPlayerRepository(tmp.resolve("p.json"))
        val p = Player(1, "Test", 1)
        repo.save(p)
        assertNotNull(repo.findById(1))
        assertEquals("Test", repo.findById(1)?.name)
    }

    @Test fun `update existing player`() {
        val repo = JsonPlayerRepository(tmp.resolve("p.json"))
        val p = Player(1, "Test", 1)
        repo.save(p)
        repo.save(p.copy(wins = 5))
        assertEquals(5, repo.findById(1)?.wins)
    }

    @Test fun `find all players`() {
        val repo = JsonPlayerRepository(tmp.resolve("p.json"))
        repo.save(Player(1, "A", 1))
        repo.save(Player(2, "B", 2))
        assertEquals(2, repo.findAll().size)
    }
}