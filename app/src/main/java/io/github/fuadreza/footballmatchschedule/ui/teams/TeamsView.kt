package io.github.fuadreza.footballmatchschedule.ui.teams

import io.github.fuadreza.footballmatchschedule.model.Team
import io.github.fuadreza.footballmatchschedule.ui.MainView

interface TeamsView: MainView{
    fun showTeamList(data: List<Team>)
}