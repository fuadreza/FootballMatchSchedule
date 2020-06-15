package io.github.fuadreza.footballmatchschedule.api

import org.junit.Test
import org.mockito.Mock


import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class ApiRepositoryTest {

    @Test
    fun testDoRequest() {
        val apiRepository = mock(ApiRepository::class.java)
        val league = "English Premier League"
        val url = "https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?l=$league"
        print(url)
        apiRepository.doRequest(url)
        verify(apiRepository).doRequest(url)
    }
}