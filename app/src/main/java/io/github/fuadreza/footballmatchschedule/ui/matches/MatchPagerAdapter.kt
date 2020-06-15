package io.github.fuadreza.footballmatchschedule.ui.matches

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import io.github.fuadreza.footballmatchschedule.ui.nextMatch.NextMatchFragment
import io.github.fuadreza.footballmatchschedule.ui.pastMatch.PastMatchFragment

class MatchPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm){

    override fun getItem(position: Int): Fragment {
        return when (position){
            0 -> NextMatchFragment()
            else -> PastMatchFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position){
            0 -> "NEXT"
            else -> "LAST"
        }
    }
}