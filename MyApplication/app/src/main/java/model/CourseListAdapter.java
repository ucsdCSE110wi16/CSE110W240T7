package model;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.core.Context;

import java.util.ArrayList;

import javax.security.auth.callback.Callback;

import Constant.Constant;

/**
 * Created by paulszh on 2/13/16.
 */
public class CourseListAdapter{
    private ArrayList retrieveCourse;
    //private Callback mCallback;
    private Firebase myData;

    // Get a reference to our posts


    public CourseListAdapter(String URL) {
        Firebase ref = new Firebase(Constant.DBURLszh);
        ref.addChildEventListener(new CourseChildEventListener());
    }


    class CourseChildEventListener implements ChildEventListener{


        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Courses existingCourse = dataSnapshot.child("CSE11").getValue(Courses.class);
            user.myCourse.add(existingCourse);
            Log.v("key", dataSnapshot.getKey());

        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            Courses existingCourse = dataSnapshot.child("CSE11").getValue(Courses.class);
            user.myCourse.add(existingCourse);
            Log.v("key", dataSnapshot.getKey());
            //Courses existingCourse = dataSnapshot.getVC(Courses.class);
            //user.myCourse.add(existingCourse);

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }
    }




}
