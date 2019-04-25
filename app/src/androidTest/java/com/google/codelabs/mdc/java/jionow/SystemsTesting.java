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
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */



@RunWith(AndroidJUnit4.class)
public class SystemsTesting {

    private UiDevice mUiDevice;

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
            MainActivity.class);


    @Before
    public void before() throws Exception {
        mUiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.google.codelabs.mdc.java.jionow", appContext.getPackageName());

    }

    //Check that the layout of OutstandingPayments has the required widgets


    @Test
    public void mainLayoutCorrect() throws InterruptedException, UiObjectNotFoundException {
        UiDevice mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        onView(withId(R.id.sign_in_button)).check(matches(isDisplayed()));
        onView(withId(R.id.sign_in_button)).perform(click());
        Thread.sleep(2000);
        UiObject mText = mUiDevice.findObject(new UiSelector().textContains("Eugene"));
        mText.clickAndWaitForNewWindow();
        onView(withId(R.id.outstandingPaymentsCardView)).check(matches(isDisplayed()));
        onView(withId(R.id.createEventsCardView)).check(matches(isDisplayed()));
        onView(withId(R.id.scanReceiptCardView)).check(matches(isDisplayed()));
        onView(withId(R.id.myEventsCardView)).check(matches(isDisplayed()));
        onView(withId(R.id.SixthRow)).check(matches(isDisplayed()));
        onView(withId(R.id.SeventhRow)).check(matches(isDisplayed()));

        //Check createevents feature
        onView(withId(R.id.createEventsCardView)).perform(click());
        onView(withId(R.id.event_edit_text)).check(matches(isDisplayed()));
        onView(withId(R.id.event_text_input)).check(matches(isDisplayed()));
        onView(withId(R.id.description_text_input)).check(matches(isDisplayed()));
        onView(withId(R.id.description_edit_text)).check(matches(isDisplayed()));
        onView(withId(R.id.start_DateTime_edit_text)).check(matches(isDisplayed()));
        onView(withId(R.id.start_DateTime_text_input)).check(matches(isDisplayed()));
        onView(withId(R.id.end_DateTime_edit_text)).check(matches(isDisplayed()));
        onView(withId(R.id.end_DateTime_text_input)).check(matches(isDisplayed()));
        onView(withId(R.id.invitees_edit_text)).check(matches(isDisplayed()));
        onView(withId(R.id.invitees_text_input)).check(matches(isDisplayed()));
        onView(withId(R.id.save_button)).check(matches(isDisplayed()));
        mDevice.pressBack();
        Thread.sleep(1500);

        //Check Camera Scan Feature
        onView(withId(R.id.scanReceiptCardView)).perform(click());
        onView(withId(R.id.spinner)).check(matches(isDisplayed()));
        onView(withId(R.id.next)).check(matches(isDisplayed()));
        Thread.sleep(2000);
        onView(withId(R.id.next)).perform(click());
        onView(withId(R.id.pickimage)).check(matches(isDisplayed()));
        onView(withId(R.id.identify)).check(matches(isDisplayed()));
        mDevice.pressBack();
        mDevice.pressBack();
        Thread.sleep(1500);

        //Check My Events Feature
        onView(withId(R.id.myEventsCardView)).perform(click());
        onView(withId(R.id.recycler_view_events)).check(matches(isDisplayed()));
        onView(withId(R.id.recycler_view_events)).perform(
                RecyclerViewActions.actionOnItemAtPosition
                        (0, SystemsTesting.MyViewAction.clickChildViewWithId(R.id. button_view_event)));
        onView(withId(R.id.text_view_event_details_name)).check(matches(isDisplayed()));
        sleep(6000);                                                                         // performance testing
        onView(withId(R.id.text_view_event_details_description)).check(matches(isDisplayed()));
        onView(withId(R.id.text_view_event_details_startdate)).check(matches(isDisplayed()));
        onView(withId(R.id.text_view_event_details_enddate)).check(matches(isDisplayed()));
        onView(withId(R.id.text_view_event_details_Host)).check(matches(isDisplayed()));
        onView(withId(R.id.text_view_event_details_participants)).check(matches(isDisplayed()));
        onView(withId(R.id.text_view_event_details_viewbill)).check(matches(isDisplayed()));
        mDevice.pressBack();
        mDevice.pressBack();
        Thread.sleep(4000);

        //Check Outstanding Payments Feature
        onView(withId(R.id.outstandingPaymentsCardView)).perform(click());
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));

        //Check pay feature
        onView(withId(R.id.recycler_view)).perform(
                RecyclerViewActions.actionOnItemAtPosition
                        (0, SystemsTesting.MyViewAction.clickChildViewWithId(R.id.button_pay)));
        onView(withId(R.id.webview)).check(matches(isDisplayed()));
        Thread.sleep(1500);
        mDevice.pressBack();

        //Check remind feature
        onView(withId(R.id.recycler_view)).perform(
                RecyclerViewActions.actionOnItemAtPosition
                        (0, SystemsTesting.MyViewAction.clickChildViewWithId(R.id. button_remindall)));
        onView(withId(R.id.webview)).check(matches(isDisplayed()));
        Thread.sleep(1500);
        mDevice.pressBack();

        //Check view outstanding payments feature
        onView(withId(R.id.recycler_view)).perform(
                RecyclerViewActions.actionOnItemAtPosition
                        (0, SystemsTesting.MyViewAction.clickChildViewWithId(R.id. button_viewpayments)));
        onView(withId(R.id.owedheader)).check(matches(isDisplayed()));
        mDevice.pressBack();
        mDevice.pressBack();




    }



}

