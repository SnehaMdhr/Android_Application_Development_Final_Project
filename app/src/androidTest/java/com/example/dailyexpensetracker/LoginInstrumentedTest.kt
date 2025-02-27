package com.example.dailyexpensetracker

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.dailyexpensetracker.ui.activity.LoginActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class LoginInstrumentedTest {
    @get:Rule
    val testRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun checkLogin() {
        onView(withId(R.id.loginEmail)).perform(
            typeText("snehamanandhar54@gmail.com")
        )

        onView(withId(R.id.loginPassword)).perform(
            typeText("987654321")
        )

        closeSoftKeyboard()

        Thread.sleep(1500)

        onView(withId(R.id.loginBtn)).perform(
            click()
        )

        Thread.sleep(1500)
        onView(withId(R.id.instrumentedCheck)).check(matches(withText("Login success")))
    }

}