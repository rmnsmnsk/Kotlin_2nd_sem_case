package repository

class MatchRepositoryImpl: MatchRepository{

    private val matches = mutableListOf<GameRecord>()

    override fun save(gameRecord: GameRecord){
        matches.add(gameRecord)
    }
    override fun findAll(): List<GameRecord>{
        return matches.toList()
    }
}