package io.github.fuadreza.footballmatchschedule.ui.detailMatch

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import io.github.fuadreza.footballmatchschedule.R
import io.github.fuadreza.footballmatchschedule.R.color.colorAccent
import io.github.fuadreza.footballmatchschedule.R.drawable.ic_add_to_favorites
import io.github.fuadreza.footballmatchschedule.R.drawable.ic_added_to_favorites
import io.github.fuadreza.footballmatchschedule.R.id.add_to_favorite
import io.github.fuadreza.footballmatchschedule.R.menu.favorite_menu
import io.github.fuadreza.footballmatchschedule.api.ApiRepository
import io.github.fuadreza.footballmatchschedule.api.TheSportDBApi
import io.github.fuadreza.footballmatchschedule.db.Favorite
import io.github.fuadreza.footballmatchschedule.db.database
import io.github.fuadreza.footballmatchschedule.model.Match
import io.github.fuadreza.footballmatchschedule.model.TeamResponse
import io.github.fuadreza.footballmatchschedule.util.invisible
import io.github.fuadreza.footballmatchschedule.util.toTanggal
import io.github.fuadreza.footballmatchschedule.util.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class DetailMatchMatchActivity : AppCompatActivity(), DetailMatchView {
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var matchPresenter: DetailMatchPresenter
    private var match: MutableList<Match> = mutableListOf()
    private var menuItem: Menu? = null
    private lateinit var id: String
    private var idTeam: String = ""
    private var isFavorite: Boolean = false

    private lateinit var dateMatch: TextView
    private lateinit var logoHomeTeam: ImageView
    private lateinit var logoAwayTeam: ImageView
    private lateinit var nameHomeTeam: TextView
    private lateinit var nameAwayTeam: TextView
    private lateinit var scoreHomeTeam: TextView
    private lateinit var scoreAwayTeam: TextView
    private lateinit var homeGoal: TextView
    private lateinit var awayGoal: TextView
    private lateinit var homeShots: TextView
    private lateinit var awayShots: TextView
    private lateinit var homeGoalKeeper: TextView
    private lateinit var awayGoalKeeper: TextView
    private lateinit var homeDefense: TextView
    private lateinit var awayDefense: TextView
    private lateinit var homeMidfield: TextView
    private lateinit var awayMidfield: TextView
    private lateinit var homeForward: TextView
    private lateinit var awayForward: TextView
    private lateinit var homeSubstitutes: TextView
    private lateinit var awaySubstitutes: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        id = intent.getStringExtra(getString(R.string.id))

        supportActionBar?.title = getString(R.string.match_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            backgroundColor = Color.WHITE

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                scrollView {
                    isVerticalScrollBarEnabled = false
                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent)

                        linearLayout {
                            lparams(width = matchParent, height = wrapContent)
                            padding = dip(10)
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER_HORIZONTAL

                            dateMatch = textView {
                                gravity = Gravity.CENTER
                                textColor = R.color.colorOrange
                                typeface = Typeface.DEFAULT_BOLD
                            }.lparams(width = matchParent, height = wrapContent)

                            linearLayout {
                                lparams(width = matchParent, height = wrapContent)
                                orientation = LinearLayout.HORIZONTAL

                                linearLayout {
                                    lparams(width = dip(0), height = wrapContent, weight = 1F)
                                    orientation = LinearLayout.VERTICAL

                                    logoHomeTeam = imageView().lparams {
                                        width = dip(100)
                                        height = dip(100)
                                        gravity = Gravity.CENTER
                                    }

                                    nameHomeTeam = textView {
                                        typeface = Typeface.DEFAULT_BOLD
                                    }.lparams {
                                        width = wrapContent
                                        height = wrapContent
                                        gravity = Gravity.CENTER
                                    }

                                    scoreHomeTeam = textView {
                                        typeface = Typeface.DEFAULT_BOLD
                                        textSize = 30F
                                    }.lparams {
                                        width = wrapContent
                                        height = wrapContent
                                        gravity = Gravity.CENTER
                                    }
                                }

                                textView {
                                    text = context.getString(R.string.vs)
                                    typeface = Typeface.DEFAULT_BOLD
                                    gravity = Gravity.CENTER_VERTICAL
                                }.lparams(width = wrapContent, height = wrapContent)

                                linearLayout {
                                    lparams(width = dip(0), height = wrapContent, weight = 1F)
                                    orientation = LinearLayout.VERTICAL

                                    logoAwayTeam = imageView().lparams {
                                        width = dip(100)
                                        height = dip(100)
                                        gravity = Gravity.CENTER
                                    }

                                    nameAwayTeam = textView {
                                        typeface = Typeface.DEFAULT_BOLD
                                    }.lparams {
                                        width = wrapContent
                                        height = wrapContent
                                        gravity = Gravity.CENTER
                                    }

                                    scoreAwayTeam = textView {
                                        typeface = Typeface.DEFAULT_BOLD
                                        textSize = 30F
                                    }.lparams {
                                        width = wrapContent
                                        height = wrapContent
                                        gravity = Gravity.CENTER
                                    }
                                }
                            }

                            linearLayout {
                                lparams(width = matchParent, height = wrapContent)
                                orientation = LinearLayout.VERTICAL

                                linearLayout {
                                    lparams(width = matchParent, height = wrapContent)
                                    padding = dip(10)

                                    homeGoal = textView().lparams(width = dip(50), height = wrapContent)
                                    textView {
                                        text = context.getString(R.string.goals)
                                        gravity = Gravity.CENTER
                                    }.lparams(width = dip(0), height = wrapContent, weight = 1F)
                                    awayGoal = textView {
                                        gravity = Gravity.RIGHT
                                    }.lparams(width = dip(50), height = wrapContent)

                                }

                                linearLayout {
                                    lparams(width = matchParent, height = wrapContent)
                                    padding = dip(10)

                                    homeShots = textView().lparams(width = dip(50), height = wrapContent)
                                    textView {
                                        text = "Shots"
                                        gravity = Gravity.CENTER
                                    }.lparams(width = dip(0), height = wrapContent, weight = 1F)
                                    awayShots = textView {
                                        gravity = Gravity.RIGHT
                                    }.lparams(width = dip(50), height = wrapContent)

                                }

                                textView {
                                    gravity = Gravity.CENTER_HORIZONTAL
                                    typeface = Typeface.DEFAULT_BOLD
                                    padding = dip(10)
                                }.lparams(width = matchParent, height = wrapContent)

                                linearLayout {
                                    lparams(width = matchParent, height = wrapContent)
                                    padding = dip(10)

                                    homeGoalKeeper = textView().lparams(width = dip(50), height = wrapContent)
                                    textView {
                                        text = context.getString(R.string.gk)
                                        gravity = Gravity.CENTER
                                    }.lparams(width = dip(0), height = wrapContent, weight = 1F)
                                    awayGoalKeeper = textView {
                                        gravity = Gravity.RIGHT
                                    }.lparams(width = dip(50), height = wrapContent)

                                }

                                linearLayout {
                                    lparams(width = matchParent, height = wrapContent)
                                    padding = dip(10)

                                    homeDefense = textView().lparams(width = dip(50), height = wrapContent)
                                    textView {
                                        text = context.getString(R.string.def)
                                        gravity = Gravity.CENTER
                                    }.lparams(width = dip(0), height = wrapContent, weight = 1F)
                                    awayDefense = textView {
                                        gravity = Gravity.RIGHT
                                    }.lparams(width = dip(50), height = wrapContent)

                                }

                                linearLayout {
                                    lparams(width = matchParent, height = wrapContent)
                                    padding = dip(10)

                                    homeMidfield = textView().lparams(width = dip(50), height = wrapContent)
                                    textView {
                                        text = context.getString(R.string.mid)
                                        gravity = Gravity.CENTER
                                    }.lparams(width = dip(0), height = wrapContent, weight = 1F)
                                    awayMidfield = textView {
                                        gravity = Gravity.RIGHT
                                    }.lparams(width = dip(50), height = wrapContent)
                                }

                                linearLayout {
                                    lparams(width = matchParent, height = wrapContent)
                                    padding = dip(10)

                                    homeForward = textView().lparams(width = dip(50), height = wrapContent)
                                    textView {
                                        text = context.getString(R.string.fwd)
                                        gravity = Gravity.CENTER
                                    }.lparams(width = dip(0), height = wrapContent, weight = 1F)
                                    awayForward = textView {
                                        gravity = Gravity.RIGHT
                                    }.lparams(width = dip(50), height = wrapContent)
                                }

                                linearLayout {
                                    lparams(width = matchParent, height = wrapContent)
                                    padding = dip(10)

                                    homeSubstitutes = textView().lparams(width = dip(50), height = wrapContent)
                                    textView {
                                        text = context.getString(R.string.subtitutes)
                                        gravity = Gravity.CENTER
                                    }.lparams(width = dip(0), height = wrapContent, weight = 1F)
                                    awaySubstitutes = textView {
                                        gravity = Gravity.RIGHT
                                    }.lparams(width = dip(50), height = wrapContent)
                                }
                            }
                        }
                        progressBar = progressBar().lparams {
                            centerHorizontally()
                        }
                    }
                }
            }
        }

        favoriteState()
        val request = ApiRepository()
        val gson = Gson()
        matchPresenter = DetailMatchPresenter(this, request, gson)
        matchPresenter.getEventDetail(id)
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showEventDetail(data: List<Match>) {
        match.clear()
        match.addAll(data)

        val tgl = data[0].eventDate
        dateMatch.text = toTanggal(tgl)

        nameHomeTeam.text = data[0].homeTeamName
        nameAwayTeam.text = data[0].awayTeamName
        scoreHomeTeam.text = data[0].homeScore
        scoreAwayTeam.text = data[0].awayScore

        homeGoal.text = data[0].homeGoal
        awayGoal.text = data[0].awayGoal
        homeShots.text = data[0].homeShots
        awayShots.text = data[0].awayShots

        homeGoalKeeper.text = data[0].homeGoalKeeper
        awayGoalKeeper.text = data[0].awayGoalKeeper
        homeDefense.text = data[0].homeDefense
        awayDefense.text = data[0].awayDefense
        homeMidfield.text = data[0].homeMidfield
        awayMidfield.text = data[0].awayMidfield
        homeForward.text = data[0].homeForward
        awayForward.text = data[0].awayForward
        homeSubstitutes.text = data[0].homeSubstitutes
        awaySubstitutes.text = data[0].awaySubstitutes

        val request = ApiRepository()
        val gson = Gson()

        idTeam = data[0].homeTeamId
        setLogo(applicationContext, idTeam, logoHomeTeam, request, gson)

        idTeam = data[0].awayTeamId
        setLogo(applicationContext, idTeam, logoAwayTeam, request, gson)
    }

    private fun setLogo(applicationContext: Context, id: String, logo: ImageView, request: ApiRepository, gson: Gson) {
        doAsync {
            val data = gson.fromJson(request
                    .doRequest(TheSportDBApi.getTeamDetail(id)),
                    TeamResponse::class.java
            )
            uiThread {
                Glide.with(applicationContext).load(data.teams[0].teamBadge).into(logo)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(favorite_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun favoriteState() {
        database.use {
            val result = select(Favorite.TABLE_FAVORITE)
                    .whereArgs("(MATCH_ID = {id})",
                            "id" to id)
            val favorite = result.parseList(classParser<Favorite>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(Favorite.TABLE_FAVORITE,
                        Favorite.MATCH_ID to id,
                        Favorite.MATCH_DATE to dateMatch.text,
                        Favorite.HOME_TEAM to nameHomeTeam.text,
                        Favorite.HOME_SCORE to scoreHomeTeam.text,
                        Favorite.AWAY_TEAM to nameAwayTeam.text,
                        Favorite.AWAY_SCORE to scoreAwayTeam.text)
            }
            snackbar(swipeRefresh, getString(R.string.add_favorite)).show()
        } catch (e: SQLiteConstraintException) {
            snackbar(swipeRefresh, e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(Favorite.TABLE_FAVORITE, "(MATCH_ID = {id})",
                        "id" to id)
            }
            snackbar(swipeRefresh, getString(R.string.remove_favorite)).show()
        } catch (e: SQLiteConstraintException) {
            snackbar(swipeRefresh, e.localizedMessage).show()
        }
    }
}