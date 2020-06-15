package io.github.fuadreza.footballmatchschedule.ui.detailMatch

import com.google.gson.Gson
import io.github.fuadreza.footballmatchschedule.api.ApiRepository
import io.github.fuadreza.footballmatchschedule.api.TheSportDBApi
import io.github.fuadreza.footballmatchschedule.model.MatchResponse
import io.github.fuadreza.footballmatchschedule.util.CoroutineContextProvider
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class DetailMatchPresenter(private val matchView: DetailMatchView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getEventDetail(id: String) {
        matchView.showLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getMatchDetail(id)),
                        MatchResponse::class.java
                )
            }
            matchView.showEventDetail(data.await().events)
            matchView.hideLoading()
        }
    }
}