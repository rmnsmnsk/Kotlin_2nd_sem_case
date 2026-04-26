package repository

interface class PlayerRepository{
    fun save (player: Player)
    fun find ById(id: Int): Player
    fun findAll(): List<PLayer>
}