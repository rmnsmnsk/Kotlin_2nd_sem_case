package repository

import model.GameRecord
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import kotlin.test.assertEquals

class JsonMatchRepositoryTest {

    @TempDir
    lateinit var tempDir: Path
    private lateinit var repository: JsonMatchRepository

    @BeforeEach
    fun setUp() {
        repository = JsonMatchRepository(tempDir.resolve("matches.json"))
    }

    @Test
    fun `save and find matches`() {
        val record = GameRecord(1, listOf(1, 2), 1, "", 30, 25, "2026-01-01")
        repository.save(record)
        val all = repository.findAll()
        assertEquals(1, all.size)
        assertEquals(30, all[0].blackScore)
    }
}