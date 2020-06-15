package io.github.fuadreza.footballmatchschedule.ui.teams

import android.util.Log
import com.google.gson.Gson
import io.github.fuadreza.footballmatchschedule.api.ApiRepository
import io.github.fuadreza.footballmatchschedule.api.TheSportDBApi
import io.github.fuadreza.footballmatchschedule.model.TeamResponse
import io.github.fuadreza.footballmatchschedule.util.CoroutineContextProvider
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class TeamsPresenter(private val view: TeamsView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getTeamList(league: String?) {
        view.showLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getTeams(league)),
                        TeamResponse::class.java
                )
            }

            view.showTeamList(data.await().teams)
            Log.d("TIM", "KOK 3")
            view.hideLoading()
        }
    }
}