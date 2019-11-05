package com.bishwajeet.questionnaire.view.splash

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.rule.ActivityTestRule
import com.bishwajeet.questionnaire.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*


@RunWith(AndroidJUnit4::class)
@MediumTest
class SplashActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(SplashActivity::class.java)


    @Test
    fun checkUsernameHint() {
        onView(withId(R.id.etUsername)).check(matches(withHint(R.string.hint_username)))
    }


    @Test
    fun checkIMEAction() {
        onView(withId(R.id.etUsername))
                .perform(typeText(TEST_USERANME), pressImeActionButton())


        onView(withId(R.id.tvUsername))
                .check(matches(withText(VALIDAYION_TEXT)))
    }


    @Test //This verifies SharedPreferencesManager
    fun verifyUserLogin() {
        onView(withId(R.id.etUsername))
                .perform(typeText(TEST_USERANME), closeSoftKeyboard())


        onView(withId(R.id.ivDone))
                .perform(click())


        onView(withId(R.id.tvUsername))
                .check(matches(withText(VALIDAYION_TEXT)))
    }


    @Test
    fun validateOldUser() {
        onView(withId(R.id.etUsername))
                .perform(typeText(OLD_USER), pressImeActionButton())


        onView(withId(R.id.llOldUser))
                .check(matches(isEnabled()))
    }


    companion object {

        var TEST_USERANME = "TestUser"
        var VALIDAYION_TEXT = "It's great to see you\nhere TestUser"
        var OLD_USER = "JohnDoe"
    }
}