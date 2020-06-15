package io.github.fuadreza.footballmatchschedule.ui.teams

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import io.github.fuadreza.footballmatchschedule.R.id.team_logo
import io.github.fuadreza.footballmatchschedule.R.id.team_name
import io.github.fuadreza.footballmatchschedule.model.Team
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class TeamsAdapter(private val teams: List<Team>, private val listener: (Team) -> Unit)
    : RecyclerView.Adapter<TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(TeamUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindItem(teams[position], listener)
    }

    override fun getItemCount(): Int = teams.size
}

class TeamUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(16)
                orientation = LinearLayout.HORIZONTAL

                imageView {
                    id = team_logo
                }.lparams {
                    height = dip(50)
                    width = dip(50)
                }

                textView {
                    id = team_name
                    textSize = 16f
                }.lparams {
                    margin = dip(15)
                }

            }
        }
    }

}

class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val teamLogo: ImageView = view.find(team_logo)
    private val teamName: TextView = view.find(team_name)
    private val ctx: Context = view.context

    fun bindItem(teams: Team, listener: (Team) -> Unit) {
        Glide.with(ctx).load(teams.teamBadge).into(teamLogo)
        teamName.text = teams.teamName
        itemView.onClick { listener(teams) }
    }
}