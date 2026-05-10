package repository

import model.Player

class PlayerRepositoryImpl : PlayerRepository {
    private val players = mutableListOf<Player>()

    override fun save(player: Player) {
        players.add(player)
    }

    override fun findById(id: Int): Player? {
        for (x in 0 until players.size) {
            if (players[x].getId() == id) {
                return players[x]
            }
        }
        return null
    }

    override fun findAll(): List<Player> {
        return players.toList()
    }
}