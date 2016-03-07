package androidstudio.edbud.com.myapplication;

import android.support.test.rule.ActivityTestRule;
import android.util.Log;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import login.Login;
import ui.BaseActivity;
import ui.CoursePage;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.ViewMatchers.withTagValue;


import static java.lang.Thread.sleep;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.typeCompatibleWith;

/**
 * Created by paulszh on 3/6/16.
 */
public class AddCourseTest {
    @Rule
    public ActivityTestRule<Login> activityRule = new ActivityTestRule(Login.class);


    @Test
    //Given the user has logged in and they are at the appointment page
    //attention: because we are given user has login in, you need to login inorder to test
    //username: tester1  password: 123456
    public void initialState(){

        onView(withId(R.id.EmailOption)).check(matches(withText("Email")));
        onView(withId(R.id.PasswordOption)).check(matches(withText("Password")));
        onView(withId(R.id.bLogin)).check(matches(allOf(isEnabled(), isClickable(), isDisplayed())));

    }

    @Test
    public void testAddCourse(){

        onView(withId(R.id.etEmail)).perform(typeText("test@ucsd.edu"));
        onView(withId(R.id.etPassword)).perform(typeText("123456"));
        onView(withId(R.id.bLogin)).check(matches(allOf(isEnabled(), isClickable(), isDisplayed())));
        onView(withId(R.id.bLogin)).perform(click());
        onView(withId(R.id.GPAtext))
                .check(matches(withText("Current GPA")));
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.GPAnumber)).perform(click());


        try {
            sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withContentDescription(R.string.navigation_drawer_open)).check(matches(isClickable()));
        onView(withContentDescription(R.string.navigation_drawer_open)).perform(click());
        onView(withText("Course")).perform(click());


        onView(withId(R.id.fab)).check(matches(isClickable()));
        onView(withId(R.id.fab)).perform(click());


        //Add CSE101
        onView(withId(R.id.etCourseID)).perform(typeText("CSE101"));
        onView(withId(R.id.etUnit)).perform(typeText("4"));
        onView(withId(R.id.bAddWeights)).check(matches(isClickable()));
        onView(withId(R.id.bAddWeights)).perform(click());
        onView(withId(R.id.etWeightId)).perform(typeText("HW"));
        onView(withId(R.id.etWeightPercent)).perform(typeText("25"));
        onView(withId(R.id.bAddIndividualWeight)).perform(click());

        onView(withId(R.id.bAddWeights)).perform(click());
        onView(withId(R.id.etWeightId)).perform(typeText("PA"));
        onView(withId(R.id.etWeightPercent)).perform(typeText("25"));
        onView(withId(R.id.bAddIndividualWeight)).perform(click());

        onView(withId(R.id.bAddWeights)).perform(click());
        onView(withId(R.id.etWeightId)).perform(typeText("Quiz"));
        onView(withId(R.id.etWeightPercent)).perform(typeText("10"));
        onView(withId(R.id.bAddIndividualWeight)).perform(click());

        onView(withId(R.id.bAddWeights)).perform(click());
        onView(withId(R.id.etWeightId)).perform(typeText("Midterm"));
        onView(withId(R.id.etWeightPercent)).perform(typeText("13"));
        onView(withId(R.id.bAddIndividualWeight)).perform(click());

        onView(withId(R.id.bAddWeights)).perform(click());
        onView(withId(R.id.etWeightId)).perform(typeText("Final"));
        onView(withId(R.id.etWeightPercent)).perform(typeText("17"));
        onView(withId(R.id.bAddIndividualWeight)).perform(click());

        //Click add course button
        onView(withId(R.id.bAddCourse)).perform(click());




        //Add CSE110
        onView(withContentDescription(R.string.navigation_drawer_open)).check(matches(isClickable()));
        onView(withContentDescription(R.string.navigation_drawer_open)).perform(click());
        onView(withText("Course")).perform(click());
        onView(withId(R.id.fab)).check(matches(isClickable()));
        onView(withId(R.id.fab)).perform(click());


        onView(withId(R.id.etCourseID)).perform(typeText("CSE110"));
        onView(withId(R.id.etUnit)).perform(typeText("4"));
        onView(withId(R.id.bAddWeights)).check(matches(isClickable()));
        onView(withId(R.id.bAddWeights)).perform(click());
        onView(withId(R.id.etWeightId)).perform(typeText("Project"));
        onView(withId(R.id.etWeightPercent)).perform(typeText("20"));
        onView(withId(R.id.bAddIndividualWeight)).perform(click());

        onView(withId(R.id.bAddWeights)).perform(click());
        onView(withId(R.id.etWeightId)).perform(typeText("Peer review"));
        onView(withId(R.id.etWeightPercent)).perform(typeText("7"));
        onView(withId(R.id.bAddIndividualWeight)).perform(click());

        onView(withId(R.id.bAddWeights)).perform(click());
        onView(withId(R.id.etWeightId)).perform(typeText("Quiz"));
        onView(withId(R.id.etWeightPercent)).perform(typeText("13"));
        onView(withId(R.id.bAddIndividualWeight)).perform(click());

        onView(withId(R.id.bAddWeights)).perform(click());
        onView(withId(R.id.etWeightId)).perform(typeText("Midterm"));
        onView(withId(R.id.etWeightPercent)).perform(typeText("20"));
        onView(withId(R.id.bAddIndividualWeight)).perform(click());

        onView(withId(R.id.bAddWeights)).perform(click());
        onView(withId(R.id.etWeightId)).perform(typeText("Final"));
        onView(withId(R.id.etWeightPercent)).perform(typeText("40"));
        onView(withId(R.id.bAddIndividualWeight)).perform(click());

        //Click add course button
        onView(withId(R.id.bAddCourse)).perform(click());



        onView(withContentDescription(R.string.navigation_drawer_open)).check(matches(isClickable()));
        onView(withContentDescription(R.string.navigation_drawer_open)).perform(click());
        onView(withText("Homepage")).perform(click());



        //onData(is(instanceOf(String.class))).inAdapterView(withId(R.id.lsCourses)).atPosition(0).perform(click());

        try {
            sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}

