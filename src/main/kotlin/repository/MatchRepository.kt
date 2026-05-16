package repository

import model.GameRecord

interface MatchRepository {
    fun save(gameRecord: GameRecord)
    fun findAll(): List<GameRecord>
}