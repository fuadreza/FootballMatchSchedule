package io.github.fuadreza.footballmatchschedule.db

data class Favorite(val id: Long?, val matchId: String?, val matchDate: String, val homeTeam: String?, val homeScore: String?, val awayTeam: String?, val awayScore: String?) {
    companion object {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val ID: String = "ID_"
        const val MATCH_ID: String = "MATCH_ID"
        const val MATCH_DATE: String = "MATCH_DATE"
        const val HOME_TEAM: String = "HOME_TEAM"
        const val HOME_SCORE: String = "HOME_SCORE"
        const val AWAY_TEAM: String = "AWAY_TEAM"
        const val AWAY_SCORE: String = "AWAY_SCORE"
    }
}