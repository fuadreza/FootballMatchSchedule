package io.github.fuadreza.footballmatchschedule

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import io.github.fuadreza.footballmatchschedule.R.id.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest{
    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testRecyclerViewBehaviour() {
        onView(withId(listMatch))
                .check(matches(isDisplayed()))
        onView(withId(listMatch)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(2))
        onView(withId(listMatch)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))
    }

    @Test
    fun testAppBehaviour() {

        onView(withChild(withText("Chelsea"))).check(matches(isDisplayed()))
        onView(withChild(withText("Chelsea"))).perform(click())

        onView(withId(add_to_favorite))
                .check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        onView(withText("Added to favorite"))
                .check(matches(isDisplayed()))
        pressBack()

        onView(withId(bottom_navigation))
                .check(matches(isDisplayed()))
        onView(withId(favorite)).perform(click())

    }
}