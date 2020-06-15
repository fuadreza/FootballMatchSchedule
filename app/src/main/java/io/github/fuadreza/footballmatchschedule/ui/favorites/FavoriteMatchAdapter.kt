package io.github.fuadreza.footballmatchschedule.ui.favorites

import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import io.github.fuadreza.footballmatchschedule.R
import io.github.fuadreza.footballmatchschedule.db.Favorite
import io.github.fuadreza.footballmatchschedule.util.toTanggal
import org.jetbrains.anko.*

class FavoriteMatchAdapter(private val favorite: List<Favorite>, private val listener: (Favorite) -> Unit)
    : RecyclerView.Adapter<FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(MatchUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = favorite.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindItem(favorite[position], listener)
    }
}

class MatchUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(15)
                orientation = LinearLayout.VERTICAL

                textView {
                    id = R.id.date_match
                    gravity = Gravity.CENTER_HORIZONTAL
                }.lparams(height = wrapContent, width = matchParent)

                linearLayout {
                    lparams(width = matchParent, height = matchParent)
                    orientation = LinearLayout.HORIZONTAL

                    textView {
                        id = R.id.home_team
                        typeface = Typeface.DEFAULT_BOLD
                    }.lparams {
                        height = wrapContent
                        width = dip(120)
                    }

                    textView {
                        id = R.id.home_score
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
                        id = R.id.away_score
                        typeface = Typeface.DEFAULT_BOLD
                    }.lparams {
                        height = wrapContent
                        width = wrapContent
                        weight = 1F
                    }

                    textView {
                        id = R.id.away_team
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

class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val homeTeam: TextView = view.find(R.id.home_team)
    private val awayTeam: TextView = view.find(R.id.away_team)
    private val homeScore: TextView = view.find(R.id.home_score)
    private val awayScore: TextView = view.find(R.id.away_score)
    private val dateMatch: TextView = view.find(R.id.date_match)

    fun bindItem(match: Favorite, listener: (Favorite) -> Unit) {
        homeTeam.text = match.homeTeam
        homeScore.text = match.homeScore
        awayTeam.text = match.awayTeam
        awayScore.text = match.awayScore

        dateMatch.text = match.matchDate

        itemView.setOnClickListener { listener(match) }
    }
}