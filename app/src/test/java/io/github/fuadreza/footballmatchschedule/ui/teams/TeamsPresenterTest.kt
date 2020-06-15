package io.github.fuadreza.footballmatchschedule.ui.teams

import com.google.gson.Gson
import io.github.fuadreza.footballmatchschedule.TestContextProvider
import io.github.fuadreza.footballmatchschedule.api.ApiRepository
import io.github.fuadreza.footballmatchschedule.api.TheSportDBApi
import io.github.fuadreza.footballmatchschedule.model.Team
import io.github.fuadreza.footballmatchschedule.model.TeamResponse
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class TeamsPresenterTest {

    @Mock
    private
    lateinit var view: TeamsView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: TeamsPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = TeamsPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testGetTeamList() {
        val teams: MutableList<Team> = mutableListOf()
        val response = TeamResponse(teams)

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeams("English Premier League")),
                TeamResponse::class.java
        )).thenReturn(response)

        presenter.getTeamList("English Premier League")

//        print(view.showTeamList(teams))

        verify(view).showLoading()
        verify(view).showTeamList(teams)
        verify(view).hideLoading()
    }
}