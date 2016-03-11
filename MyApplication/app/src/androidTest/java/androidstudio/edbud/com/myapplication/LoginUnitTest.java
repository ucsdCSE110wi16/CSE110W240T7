package androidstudio.edbud.com.myapplication;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.fasterxml.jackson.databind.deser.Deserializers;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import login.Login;
import model.IndividualCourse;
import ui.BaseActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Created by paulszh on 3/11/16.
 */





@RunWith(AndroidJUnit4.class)
public class LoginUnitTest {
    @Rule
    public ActivityTestRule<Login> activityRule = new ActivityTestRule(Login.class);


    @Test
    //*********************************************************
    //username: test@ucsd.edu  password: 123456
    //Test if we get the data from firebase correctly
    //**********************************************************
    public void testUserInfoOnFireBase(){
        onView(withId(R.id.etEmail)).perform(typeText("test@ucsd.edu"));
        onView(withId(R.id.etPassword)).perform(typeText("123456"));
        onView(withId(R.id.bLogin)).check(matches(allOf(isEnabled(), isClickable(), isDisplayed())));
        onView(withId(R.id.bLogin)).perform(click());
        String ID = BaseActivity.initialize.getUid();
        Firebase ref = new Firebase("https://edbud.firebaseio.com/userInfo/" + ID);
        assertEquals(ref.getKey(), ID);


    }


}
