package io.github.fuadreza.footballmatchschedule.ui.nextMatch

import io.github.fuadreza.footballmatchschedule.model.Match
import io.github.fuadreza.footballmatchschedule.ui.MainView

interface NextMatchView : MainView {
    fun showEventList(data: List<Match>)
}