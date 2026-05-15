package repository

import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import model.GameRecord
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

class JsonMatchRepository(private val filePath: Path = Paths.get("matches.json")) : MatchRepository {
    private val json = Json { prettyPrint = true; ignoreUnknownKeys = true }

    override fun save(record: GameRecord) {
        val list = loadAll().toMutableList()
        list.add(record)
        Files.writeString(filePath, json.encodeToString(list), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)
    }
    override fun findAll(): List<GameRecord> = loadAll()
    private fun loadAll(): List<GameRecord> = try {
        if (!Files.exists(filePath)) emptyList() else json.decodeFromString(Files.readString(filePath))
    } catch (e: Exception) { emptyList() }
}