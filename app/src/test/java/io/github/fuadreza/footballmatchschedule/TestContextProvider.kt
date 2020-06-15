package io.github.fuadreza.footballmatchschedule

import io.github.fuadreza.footballmatchschedule.util.CoroutineContextProvider
import kotlinx.coroutines.experimental.Unconfined
import kotlin.coroutines.experimental.CoroutineContext

class TestContextProvider : CoroutineContextProvider() {
    override val main: CoroutineContext = Unconfined
}