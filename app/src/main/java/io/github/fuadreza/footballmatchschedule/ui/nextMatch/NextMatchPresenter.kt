package io.github.fuadreza.footballmatchschedule.ui.nextMatch

import com.google.gson.Gson
import io.github.fuadreza.footballmatchschedule.api.ApiRepository
import io.github.fuadreza.footballmatchschedule.api.TheSportDBApi
import io.github.fuadreza.footballmatchschedule.model.MatchResponse
import io.github.fuadreza.footballmatchschedule.util.CoroutineContextProvider
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class NextMatchPresenter(private val view: NextMatchView,
                         private val apiRepository: ApiRepository,
                         private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()) {
    fun getNextEventList(league: String?) {
        view.showLoading()

        async(context.main){
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getNextMatch(league)),
                        MatchResponse::class.java
                )
            }
            view.showEventList(data.await().events)
            view.hideLoading()
        }
    }
}