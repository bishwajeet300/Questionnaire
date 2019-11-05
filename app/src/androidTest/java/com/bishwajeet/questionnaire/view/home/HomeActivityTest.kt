package com.bishwajeet.questionnaire.view.home

import android.content.Intent
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.bishwajeet.questionnaire.R
import com.bishwajeet.questionnaire.utils.INTENT_OPERATION_MODE
import com.bishwajeet.questionnaire.utils.INTENT_READ_MODE
import com.bishwajeet.questionnaire.utils.INTENT_WRITE_MODE
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@MediumTest
class HomeActivityTest {


    @get:Rule
    val activityRule = ActivityTestRule<HomeActivity>(HomeActivity::class.java, true, false)


    @Test
    fun verifyIntentInformation_ReadMode() {

        val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
        val intent = Intent(targetContext, HomeActivity::class.java)
        intent.putExtra(INTENT_OPERATION_MODE, INTENT_READ_MODE)

        activityRule.launchActivity(intent)

        Espresso.onView(ViewMatchers.withId(R.id.tvTitle)).check(ViewAssertions.matches(ViewMatchers.withText(R.string.app_name_view)))
    }


    @Test
    fun verifyIntentInformation_WriteMode() {

        val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
        val intent = Intent(targetContext, HomeActivity::class.java)
        intent.putExtra(INTENT_OPERATION_MODE, INTENT_WRITE_MODE)

        activityRule.launchActivity(intent)

        Espresso.onView(ViewMatchers.withId(R.id.tvTitle)).check(ViewAssertions.matches(ViewMatchers.withText(R.string.app_name)))
    }


    @Test
    fun verifyViewPagerSwipe() {
//        val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
//        val intent = Intent(targetContext, HomeActivity::class.java)
//        intent.putExtra(INTENT_OPERATION_MODE, INTENT_READ_MODE)
//
//        activityRule.launchActivity(intent)
//
//        Espresso.onView(ViewMatchers.withId(R.id.vpQuestions))
//                .perform(ViewActions.swipeLeft())
//
//
//        Espresso.onView(ViewMatchers.withId(R.id.tvQuestions))
//                .check(ViewAssertions.matches(ViewMatchers.withText(startName)))

    }


    companion object {
        var startName = "1/25 questions"
        var endName = "2/25 questions"
    }
}