package io.github.fuadreza.footballmatchschedule.util

import android.view.View

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun toTanggal(tgl: String?): String{
    return tgl?.substring(8, 10) + "-" + tgl?.substring(5, 7) + "-" + tgl?.substring(0, 4)
}

fun toEventId(league: String?): String{
    return when (league){
        "English Premier League" -> "4328"
        "English League Championship" -> "4329"
        "German Bundesliga" -> "4331"
        "Italian Serie A" -> "4332"
        "French Ligue 1" -> "4334"
        "Spanish La Liga" -> "4335"
        else -> "0"
    }
}