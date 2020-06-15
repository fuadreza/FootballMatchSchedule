package io.github.fuadreza.footballmatchschedule.ui.detailMatch

import io.github.fuadreza.footballmatchschedule.model.Match
import io.github.fuadreza.footballmatchschedule.ui.MainView

interface DetailMatchView : MainView {
    fun showEventDetail(data: List<Match>)
}