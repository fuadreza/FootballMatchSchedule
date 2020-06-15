package io.github.fuadreza.footballmatchschedule.api

import io.github.fuadreza.footballmatchschedule.BuildConfig

object TheSportDBApi {

    private val url = BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}"

    fun getPastMatch(league: String?): String {
        return "$url/eventspastleague.php?id=$league"
    }

    fun getNextMatch(league: String?): String {
        return "$url/eventsnextleague.php?id=$league"
    }

    fun getMatchDetail(id: String): String {
        return "$url/lookupevent.php?id=$id"
    }

    fun getTeams(league: String?): String {
        return "$url/search_all_teams.php?l=$league"
    }

    fun getTeamDetail(id: String): String {
        return "$url/lookupteam.php?id=$id"
    }

    fun getPlayers(team: String): String {
        return "$url/searchplayers.php?t=$team"
    }

    fun getPlayerDetail(id: String): String {
        return "$url/lookupplayer.php?id=$id"
    }
}