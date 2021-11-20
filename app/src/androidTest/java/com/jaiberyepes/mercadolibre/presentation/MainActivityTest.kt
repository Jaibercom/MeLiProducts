package com.jaiberyepes.mercadolibre.presentation

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import com.jaiberyepes.mercadolibre.R
import com.jaiberyepes.mercadolibre.presentation.ui.MainActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        UiDevice.getInstance(getInstrumentation())
            .wait(Until.hasObject(By.text("Walter White")), TIME_OUT)
    }

    @Test
    fun characterListTest() {
        onView(
            allOf(
                withId(R.id.item_title), withText("Walter White")
            )
        ).check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.item_price), withText("Heisenberg")
            )
        ).check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.item_title), withText("Jesse Pinkman")
            )
        ).check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.item_price), withText("Cap n' Cook")
            )
        ).check(matches(isDisplayed()))
    }

    @Test
    fun characterDetailTest() {
        onView(
            allOf(
                withId(R.id.item_price), withText("Heisenberg")
            )
        ).check(matches(isDisplayed()))

        onView(
            allOf(
                childAtPosition(
                    allOf(
                        withId(R.id.searchEpoxyRecyclerView),
                        childAtPosition(
                            withId(R.id.charactersLayout),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        ).perform(click())

        UiDevice.getInstance(getInstrumentation())
            .wait(Until.hasObject(By.text("Heisenberg")), TIME_OUT)

        onView(
            allOf(
                withId(R.id.nickNameTextView), withText("Heisenberg")
            )
        ).check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.occupationTextView), withText("High School Chemistry Teacher")
            )
        ).check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.statusTextView), withText("Presumed dead")
            )
        ).check(matches(withText("Presumed dead")))

        onView(
            allOf(
                withId(R.id.portrayedTextView), withText("Bryan Cranston")
            )
        ).check(matches(isDisplayed()))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

    companion object {
        private const val TIME_OUT = 5000L
    }
}
