package io.github.fuadreza.footballmatchschedule.ui.pastMatch

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
import org.mockito.MockitoAnnotations

class PastMatchPresenterTest {

    @Mock
    private
    lateinit var view: PastMatchView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: PastMatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = PastMatchPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testGetEventList() {
        val match: MutableList<Match> = mutableListOf()
        val response = MatchResponse(match)

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getPastMatch("4328")),
                MatchResponse::class.java
        )).thenReturn(response)

        presenter.getEventList("4328")

        verify(view).showLoading()
        verify(view).showEventList(match)
        verify(view).hideLoading()
    }
}