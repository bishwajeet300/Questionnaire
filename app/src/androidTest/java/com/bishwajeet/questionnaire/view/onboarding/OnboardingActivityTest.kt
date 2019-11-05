package com.bishwajeet.questionnaire.view.onboarding

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.rule.ActivityTestRule
import com.bishwajeet.questionnaire.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class OnboardingActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(OnboardingActivity::class.java)


    @Test
    fun checkAppName() {
        Espresso.onView(ViewMatchers.withId(R.id.tvTitle)).check(ViewAssertions.matches(ViewMatchers.withText(R.string.app_name)))
    }


    @Test
    fun checkButtonText() {
        Espresso.onView(ViewMatchers.withId(R.id.tvNewUser)).check(ViewAssertions.matches(ViewMatchers.withText(R.string.new_user)))

        Espresso.onView(ViewMatchers.withId(R.id.tvOldUser)).check(ViewAssertions.matches(ViewMatchers.withText(R.string.old_user)))
    }

}