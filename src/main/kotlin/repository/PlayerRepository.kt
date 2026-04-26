package repository

import model.Player

interface PlayerRepository {
    fun save(player: Player)
    fun findById(id: Int): Player?
    fun findAll(): List<Player>
}