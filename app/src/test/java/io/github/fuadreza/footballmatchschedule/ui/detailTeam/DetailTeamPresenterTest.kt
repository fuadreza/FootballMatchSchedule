package io.github.fuadreza.footballmatchschedule.ui.detailTeam

import com.google.gson.Gson
import io.github.fuadreza.footballmatchschedule.TestContextProvider
import io.github.fuadreza.footballmatchschedule.api.ApiRepository
import io.github.fuadreza.footballmatchschedule.api.TheSportDBApi
import io.github.fuadreza.footballmatchschedule.model.Team
import io.github.fuadreza.footballmatchschedule.model.TeamResponse
import io.github.fuadreza.footballmatchschedule.ui.detailMatch.DetailMatchPresenter
import io.github.fuadreza.footballmatchschedule.ui.detailMatch.DetailMatchView
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations.initMocks

class DetailTeamPresenterTest {

    @Mock
    private
    lateinit var view: DetailTeamView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: DetailTeamPresenter

    @Before
    fun setUp() {
        initMocks(this)
        presenter = DetailTeamPresenter(view, apiRepository, gson, TestContextProvider())
    }


    @Test
    fun testGetTeamDetail() {
        val team: MutableList<Team> = mutableListOf()
        val response = TeamResponse(team)

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeamDetail("133610")),
                TeamResponse::class.java
        )).thenReturn(response)

        presenter.getTeamDetail("133610")

        verify(view).showLoading()
        verify(view).showTeamDetail(team)
        verify(view).hideLoading()
    }
}