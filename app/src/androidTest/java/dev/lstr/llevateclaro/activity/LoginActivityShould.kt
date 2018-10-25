package dev.lstr.llevateclaro.activity

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import dev.lstr.llevateclaro.R
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
class LoginActivityShould {

    @get:Rule
    private val activityTestRule = ActivityTestRule(LoginActivity::class.java, true, false)

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun validateUserFailed() {
        val intent = Intent()
        activityTestRule.launchActivity(intent)

        onView(withId(R.id.v_root)).check(matches(isDisplayed()))
        onView(withId(R.id.txt_usuario)).perform(clearText(),typeText("pepe2@yopmail.com"))
        onView(withId(R.id.txt_password)).perform(clearText(),typeText("11111"))
        onView(withId(R.id.btn_ingresar)).perform(click())
        onView(withId(R.id.v_root)).check(matches(isDisplayed()))
    }

    @Test
    fun validateUserSuccessful() {
        val intent = Intent()
        activityTestRule.launchActivity(intent)

        onView(withId(R.id.v_root)).check(matches(isDisplayed()))
        onView(withId(R.id.txt_usuario)).perform(clearText(),typeText("pepe2@yopmail.com"))
        onView(withId(R.id.txt_password)).perform(clearText(),typeText("12345"))
        onView(withId(R.id.btn_ingresar)).perform(click())
        onView(withId(R.id.drawer_layout)).check(matches(isDisplayed()))
    }

}