package androidstudio.edbud.com.myapplication;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.TypeTextAction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import login.Login;
import model.Courses;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.onData;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.doubleClick;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static java.lang.Thread.sleep;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

/**
 * Created by paulszh on 3/7/16.
 */

//Before running this testCase, make sure the RegisterandLoginTest has already been run


public class AddAssignmentTest {
    @Rule
    public ActivityTestRule<Login> activityRule = new ActivityTestRule(Login.class);


    @Test
    //Given the user has logged in and they are at the appointment page
    //attention: because we are given user has login in, you need to login inorder to test
    //username: test@ucsd.edu  password: 123456
    public void initialState(){

        onView(withId(R.id.EmailOption)).check(matches(withText("Email")));
        onView(withId(R.id.PasswordOption)).check(matches(withText("Password")));
        onView(withId(R.id.bLogin)).check(matches(allOf(isEnabled(), isClickable(), isDisplayed())));

    }

    @Test
    public void testAddAssignment() {

        onView(withId(R.id.etEmail)).perform(typeText("test@ucsd.edu"));
        onView(withId(R.id.etPassword)).perform(typeText("123456"));
        onView(withId(R.id.bLogin)).check(matches(allOf(isEnabled(), isClickable(), isDisplayed())));
        onView(withId(R.id.bLogin)).perform(click());
        onView(withContentDescription(R.string.navigation_drawer_open)).check(matches(isClickable()));
        onView(withContentDescription(R.string.navigation_drawer_open)).perform(click());
        onView(withText("Course")).perform(click());


        onData(is(instanceOf(Courses.class))).inAdapterView(withId(R.id.lsCourses)).atPosition(0).perform(click());

        onView(withId(R.id.individual_fab)).check(matches(isClickable()));
        onView(withId(R.id.individual_fab)).perform(click());

        onView(withId(R.id.etAssignmentID)).perform(typeText("Midterm1"));
        onView(withId(R.id.etDueDate)).perform(doubleClick());
        onView(withText("OK")).perform(click());
        onView(withText("Midterm")).perform(click());
        onView(withId(R.id.bAddAssignment)).perform(click());

        onView(withId(R.id.individual_fab)).check(matches(isClickable()));
        onView(withId(R.id.individual_fab)).perform(click());

        onView(withId(R.id.etAssignmentID)).perform(typeText("Midterm2"));
        onView(withId(R.id.etDueDate)).perform(doubleClick());
        onView(withText("OK")).perform(click());
        onView(withText("Midterm")).perform(click());
        onView(withId(R.id.bAddAssignment)).perform(click());


        try {
            sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }




    }
}
