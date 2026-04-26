package repository

interface MatchRepository {
    fun save(gameRecord: GameRecord)
    fun findAll(): List<GameRecord>
}