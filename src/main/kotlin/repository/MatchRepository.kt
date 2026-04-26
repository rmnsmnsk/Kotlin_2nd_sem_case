package repository

interface class MacthRepository{
    fun save(gameRecord: GameRecord)
    fun findAll(): List<GameRecord>
}