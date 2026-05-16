package repository

import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import model.Player
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

class JsonPlayerRepository(private val filePath: Path = Paths.get("players.json")) : PlayerRepository {
    private val json = Json { prettyPrint = true; ignoreUnknownKeys = true }

    override fun save(player: Player) {
        val list = loadAll().toMutableList()
        val idx = list.indexOfFirst { it.id == player.id }
        if (idx >= 0) list[idx] = player else list.add(player)
        Files.writeString(filePath, json.encodeToString(list), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)
    }
    override fun findById(id: Int): Player? = loadAll().find { it.id == id }
    override fun findAll(): List<Player> = loadAll()
    private fun loadAll(): List<Player> = try {
        if (!Files.exists(filePath)) emptyList() else json.decodeFromString(Files.readString(filePath))
    } catch (e: Exception) { emptyList() }
}