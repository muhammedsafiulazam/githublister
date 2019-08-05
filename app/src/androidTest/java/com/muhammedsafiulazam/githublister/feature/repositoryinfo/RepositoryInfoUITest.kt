package com.muhammedsafiulazam.githublister.feature.repositoryinfo

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.muhammedsafiulazam.githublister.R
import com.muhammedsafiulazam.githublister.core.BaseUITest
import com.muhammedsafiulazam.githublister.core.IAfterWait
import com.muhammedsafiulazam.githublister.core.IBeforeWait
import com.muhammedsafiulazam.githublister.event.Event
import com.muhammedsafiulazam.githublister.feature.repositorylist.RepositoryListActivity
import com.muhammedsafiulazam.githublister.feature.repositorylist.RepositoryViewHolder
import com.muhammedsafiulazam.githublister.network.event.repository.RepositoryEventType
import com.muhammedsafiulazam.githublister.utils.RecyclerViewAssertion.withItemCount
import org.hamcrest.Matchers.greaterThan
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Muhammed Safiul Azam on 29/07/2019.
 */

@RunWith(AndroidJUnit4ClassRunner::class)
class RepositoryInfoUITest : BaseUITest() {

    @Rule @JvmField
    var mActivityTestRule: ActivityTestRule<RepositoryListActivity> = ActivityTestRule(RepositoryListActivity::class.java, true, false)

    @Before
    fun beforeTest() {
    }

    @Test
    fun checkSuccess_loadRepository() {
        wait(RepositoryEventType.GET_REPOSITORIES, object : IBeforeWait {
            override fun beforeWait() {
                val intent = Intent(getContext(), RepositoryListActivity::class.java)
                mActivityTestRule.launchActivity(intent)

                onView(withId(R.id.repositorylist_pgb_loader)).check(matches(isDisplayed()))
                onView(withId(R.id.repositorylist_ryv_repositories)).check(withItemCount(0))
            }

        }, object : IAfterWait {
            override fun afterWait(events: List<Event>) {

                onView(withId(R.id.repositorylist_pgb_loader)).check(matches(not(isDisplayed())))
                onView(withId(R.id.repositorylist_ryv_repositories)).check(withItemCount(greaterThan(0)))
                onView(withId(R.id.repositorylist_ryv_repositories)).perform(RecyclerViewActions.actionOnItemAtPosition<RepositoryViewHolder>(0, click()))
            }
        })
    }

    @After
    fun afterTest() {

    }
}