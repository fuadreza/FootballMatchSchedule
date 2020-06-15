package io.github.fuadreza.footballmatchschedule.ui.detailTeam

import com.google.gson.Gson
import io.github.fuadreza.footballmatchschedule.api.ApiRepository
import io.github.fuadreza.footballmatchschedule.api.TheSportDBApi
import io.github.fuadreza.footballmatchschedule.model.TeamResponse
import io.github.fuadreza.footballmatchschedule.util.CoroutineContextProvider
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class DetailTeamPresenter(private val view: DetailTeamView,
                          private val apiRepository: ApiRepository,
                          private val gson: Gson, private val contextPool: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getTeamDetail(teamId: String) {
        view.showLoading()

        async(contextPool.main){
            val data = bg{
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getTeamDetail(teamId)),
                        TeamResponse::class.java
                )
            }

            view.showTeamDetail(data.await().teams)
            view.hideLoading()

//            TODO("Bikin activity Detail Team")
        }
    }
}