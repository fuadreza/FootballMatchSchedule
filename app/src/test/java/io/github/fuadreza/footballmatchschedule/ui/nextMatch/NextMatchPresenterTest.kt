package io.github.fuadreza.footballmatchschedule.ui.nextMatch

import com.google.gson.Gson
import io.github.fuadreza.footballmatchschedule.TestContextProvider
import io.github.fuadreza.footballmatchschedule.api.ApiRepository
import io.github.fuadreza.footballmatchschedule.api.TheSportDBApi
import io.github.fuadreza.footballmatchschedule.model.Match
import io.github.fuadreza.footballmatchschedule.model.MatchResponse
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class NextMatchPresenterTest {

    @Mock
    private
    lateinit var view: NextMatchView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: NextMatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = NextMatchPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testGetNextEventList() {
        val match: MutableList<Match> = mutableListOf()
        val response = MatchResponse(match)

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getNextMatch("4328")),
                MatchResponse::class.java
        )).thenReturn(response)

        presenter.getNextEventList("4328")

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showEventList(match)
        Mockito.verify(view).hideLoading()
    }
}