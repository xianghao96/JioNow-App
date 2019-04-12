package com.google.codelabs.mdc.java.jionow;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */



@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    public static class MyViewAction {

        public static ViewAction clickChildViewWithId(final int id) {
            return new ViewAction() {
                @Override
                public Matcher<View> getConstraints() {
                    return null;
                }

                @Override
                public String getDescription() {
                    return "Click on a child view with specified id.";
                }

                @Override
                public void perform(UiController uiController, View view) {
                    View v = view.findViewById(id);
                    v.performClick();
                }
            };
        }

    }

    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule<>(
            EventsDisplay.class);


    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.google.codelabs.mdc.java.jionow", appContext.getPackageName());

    }

    //Check that the layout of EventsDisplay has the required widgets


    @Test
    public void mainLayoutCorrect() {
        onView(withId(R.id.recycler_view_events)).check(matches(isDisplayed()));
    }

    @Test
    public void checkEventDetails() throws InterruptedException {
        onView(withId(R.id.recycler_view_events)).perform(
                RecyclerViewActions.actionOnItemAtPosition
                        (0, MyViewAction.clickChildViewWithId(R.id. button_view_event)));
        onView(withId(R.id.text_view_event_details_name)).check(matches(isDisplayed()));
        sleep(6000);                                                                         // performance testing
        onView(withId(R.id.text_view_event_details_description)).check(matches(isDisplayed()));
        onView(withId(R.id.text_view_event_details_startdate)).check(matches(isDisplayed()));
        onView(withId(R.id.text_view_event_details_enddate)).check(matches(isDisplayed()));
        onView(withId(R.id.text_view_event_details_Host)).check(matches(isDisplayed()));
        onView(withId(R.id.text_view_event_details_participants)).check(matches(isDisplayed()));
        onView(withId(R.id.text_view_event_details_viewbill)).check(matches(isDisplayed()));
        onView(withId(R.id.text_view_event_details_viewbill)).perform(click());
        onView(withId(R.id.webview)).check(matches(isDisplayed()));
    }



}

