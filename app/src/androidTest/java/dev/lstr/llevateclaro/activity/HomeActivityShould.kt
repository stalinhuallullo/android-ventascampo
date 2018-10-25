package dev.lstr.llevateclaro.activity

import android.content.Intent
import android.support.test.espresso.contrib.DrawerActions
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.NavigationViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.Gravity
import dev.lstr.llevateclaro.R
import dev.lstr.llevateclaro.presentation.ui.activity.HomeActivity
import dev.lstr.llevateclaro.presentation.ui.activity.LoginActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by lesternr on 7/1/18.
 */
@RunWith(AndroidJUnit4::class)
class HomeActivityShould {

    @get:Rule
    private val activityTestRule = ActivityTestRule(HomeActivity::class.java, true, false)

    @Before
    fun setUp() {
        val intent = Intent()
        activityTestRule.launchActivity(intent)
        activityTestRule.activity
                .supportFragmentManager.beginTransaction()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun openMenu() {
//        Thread.sleep(2000)
        onView(withId(R.id.drawer_layout))
                .perform(DrawerActions.open())
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_perfil))
//        Thread.sleep(1000)
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(ViewActions.click());
    }

    @Test
    fun openRegistroFijo() {
        onView(withId(R.id.ll_menu_fijo)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.ll_menu_fijo)).perform(ViewActions.click())
    }

    @Test
    fun openRegistroMovil() {
        onView(withId(R.id.ll_menu_movil)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.ll_menu_movil)).perform(ViewActions.click())
    }

}