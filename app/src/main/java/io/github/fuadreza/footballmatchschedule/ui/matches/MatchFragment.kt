package io.github.fuadreza.footballmatchschedule.ui.matches

import android.content.Context
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import io.github.fuadreza.footballmatchschedule.R
import io.github.fuadreza.footballmatchschedule.ui.MainView
import io.github.fuadreza.footballmatchschedule.util.invisible
import io.github.fuadreza.footballmatchschedule.util.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.design.themedTabLayout
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.viewPager

class MatchFragment: Fragment(), AnkoComponent<Context>, MainView {

    private lateinit var myViewPager: ViewPager
    private lateinit var myTabs: TabLayout
    private lateinit var progressBar: ProgressBar

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val fragmentAdapter = MatchPagerAdapter(childFragmentManager)
        myViewPager.adapter = fragmentAdapter

        myTabs.setupWithViewPager(myViewPager)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui){
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL

            myTabs = themedTabLayout(R.style.ThemeOverlay_AppCompat_Light) {
                lparams(matchParent, wrapContent)
                {
                    tabGravity = Gravity.FILL
                    tabMode = TabLayout.MODE_FIXED
                }
            }

            myViewPager = viewPager {
                id = R.id.viewpager_match
            }.lparams(matchParent, matchParent)
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }
}