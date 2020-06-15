package io.github.fuadreza.footballmatchschedule

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.fuadreza.footballmatchschedule.R.id.*
import io.github.fuadreza.footballmatchschedule.R.layout.activity_main
import io.github.fuadreza.footballmatchschedule.ui.favorites.FavoriteMatchFragment
import io.github.fuadreza.footballmatchschedule.ui.matches.MatchFragment
import io.github.fuadreza.footballmatchschedule.ui.nextMatch.NextMatchFragment
import io.github.fuadreza.footballmatchschedule.ui.pastMatch.PastMatchFragment
import io.github.fuadreza.footballmatchschedule.ui.teams.TeamsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                matches -> {
                    loadMatches(savedInstanceState)
                }
                teams -> {
                    loadTeams(savedInstanceState)
                }
                favorite -> {
                    loadFavorites(savedInstanceState)
                }
            }
            true
        }
        bottom_navigation.selectedItemId = matches
    }

    private fun loadMatches(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, MatchFragment(), MatchFragment::class.simpleName)
                    .commit()
        }
    }

    private fun loadTeams(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, TeamsFragment(), TeamsFragment::class.simpleName)
                    .commit()
        }
    }

    private fun loadFavorites(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, FavoriteMatchFragment(), FavoriteMatchFragment::class.simpleName)
                    .commit()
        }
    }
}