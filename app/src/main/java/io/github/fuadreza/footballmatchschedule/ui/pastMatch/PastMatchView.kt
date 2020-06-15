package io.github.fuadreza.footballmatchschedule.ui.pastMatch

import io.github.fuadreza.footballmatchschedule.model.Match
import io.github.fuadreza.footballmatchschedule.ui.MainView

interface PastMatchView : MainView {
    fun showEventList(data: List<Match>)
}