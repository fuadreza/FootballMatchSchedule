package io.github.fuadreza.footballmatchschedule.ui.detailMatch

import com.google.gson.Gson
import io.github.fuadreza.footballmatchschedule.TestContextProvider
import io.github.fuadreza.footballmatchschedule.api.ApiRepository
import io.github.fuadreza.footballmatchschedule.api.TheSportDBApi
import io.github.fuadreza.footballmatchschedule.model.Match
import io.github.fuadreza.footballmatchschedule.model.MatchResponse
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations.initMocks

class DetailMatchPresenterTest {

    @Mock
    private
    lateinit var matchView: DetailMatchView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var matchPresenter: DetailMatchPresenter

    @Before
    fun setUp() {
        initMocks(this)
        matchPresenter = DetailMatchPresenter(matchView, apiRepository, gson, TestContextProvider())
    }


    @Test
    fun testGetEventDetail() {
        val match: MutableList<Match> = mutableListOf()
        val response = MatchResponse(match)

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getMatchDetail("576585")),
                MatchResponse::class.java
        )).thenReturn(response)

        matchPresenter.getEventDetail("576585")

        print(match[0].homeScore)
        print(match[0].awayScore)

        verify(matchView).showLoading()
        verify(matchView).showEventDetail(match)
        verify(matchView).hideLoading()
    }
}