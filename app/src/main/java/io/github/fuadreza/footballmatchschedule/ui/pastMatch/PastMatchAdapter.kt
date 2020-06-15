package io.github.fuadreza.footballmatchschedule.ui.pastMatch

import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import io.github.fuadreza.footballmatchschedule.R
import io.github.fuadreza.footballmatchschedule.R.id.*
import io.github.fuadreza.footballmatchschedule.model.Match
import io.github.fuadreza.footballmatchschedule.util.toTanggal
import org.jetbrains.anko.*

class PastMatchAdapter(private val match: List<Match>,
                       private val listener: (Match) -> Unit)
    : RecyclerView.Adapter<MatchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        return MatchViewHolder(MatchUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bindItem(match[position], listener)
    }

    override fun getItemCount(): Int = match.size
}

class MatchUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(15)
                orientation = LinearLayout.VERTICAL

                textView {
                    id = date_match
                    gravity = Gravity.CENTER_HORIZONTAL
                }.lparams(height = wrapContent, width = matchParent)

                linearLayout {
                    lparams(width = matchParent, height = matchParent)
                    orientation = LinearLayout.HORIZONTAL

                    textView {
                        id = home_team
                        typeface = Typeface.DEFAULT_BOLD
                    }.lparams {
                        height = wrapContent
                        width = dip(120)
                    }

                    textView {
                        id = home_score
                        gravity = Gravity.RIGHT
                        typeface = Typeface.DEFAULT_BOLD
                    }.lparams {
                        height = wrapContent
                        width = wrapContent
                        weight = 1F
                    }

                    textView {
                        text = context.getString(R.string.vs)
                        gravity = Gravity.CENTER_HORIZONTAL
                    }.lparams {
                        height = wrapContent
                        width = wrapContent
                        weight = 1F
                    }

                    textView {
                        id = away_score
                        typeface = Typeface.DEFAULT_BOLD
                    }.lparams {
                        height = wrapContent
                        width = wrapContent
                        weight = 1F
                    }

                    textView {
                        id = away_team
                        typeface = Typeface.DEFAULT_BOLD
                        gravity = Gravity.RIGHT
                    }.lparams {
                        height = wrapContent
                        width = dip(120)
                    }
                }
            }
        }
    }
}

class MatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val homeTeam: TextView = view.find(home_team)
    private val awayTeam: TextView = view.find(away_team)
    private val homeScore: TextView = view.find(home_score)
    private val awayScore: TextView = view.find(away_score)
    private val dateMatch: TextView = view.find(date_match)

    fun bindItem(match: Match, listener: (Match) -> Unit) {
        homeTeam.text = match.homeTeamName
        homeScore.text = match.homeScore
        awayTeam.text = match.awayTeamName
        awayScore.text = match.awayScore

        val tgl = match.eventDate
        dateMatch.text = toTanggal(tgl)

        itemView.setOnClickListener { listener(match) }
    }
}