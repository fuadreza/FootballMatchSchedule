package io.github.fuadreza.footballmatchschedule.ui.detailTeam

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ProgressBar
import io.github.fuadreza.footballmatchschedule.ui.MainView
import io.github.fuadreza.footballmatchschedule.util.invisible
import io.github.fuadreza.footballmatchschedule.util.visible
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.collapsingToolbarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.tabLayout
import org.jetbrains.anko.dip
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.support.v4.viewPager
import org.jetbrains.anko.toolbar
import org.jetbrains.anko.wrapContent

class DetailTeamActivity : AppCompatActivity(), MainView {
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout()
    }

    private fun initLayout() {
        coordinatorLayout {
            lparams(width = matchParent, height = matchParent)
            fitsSystemWindows = true

            appBarLayout {
                lparams(width = matchParent, height = dip(100))

                collapsingToolbarLayout {
                    lparams(width = matchParent, height = dip(200))

                    toolbar {
                        lparams(width = matchParent, height = wrapContent)
                    }

                    tabLayout {
                        lparams{
                            width = matchParent
                            height = wrapContent
                            gravity = bottom
                        }
                    }

                }
            }

            viewPager().lparams(width = matchParent, height = matchParent)
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

}