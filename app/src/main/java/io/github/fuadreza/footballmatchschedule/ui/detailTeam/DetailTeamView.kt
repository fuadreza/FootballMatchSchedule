package io.github.fuadreza.footballmatchschedule.ui.detailTeam

import io.github.fuadreza.footballmatchschedule.model.Team
import io.github.fuadreza.footballmatchschedule.ui.MainView

interface DetailTeamView: MainView {
    fun showTeamDetail(data: List<Team>)
}