package model;

import android.support.design.widget.NavigationView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.TextView;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

import Constant.Constant;
import androidstudio.edbud.com.myapplication.R;

/**
 * Created by Tianqi on 2/7/2016.
 */

public class user {

    public static String email;
    public  static String fullName;
    public  static String major;
    public  static String college;
    public  static String password;
    public  static String graduateDate;


    private static int unit = 0;
    private static double gpa = 4.0;

    @JsonIgnore
    public static String UID;

    public static ArrayList<IndividualAssignment> recentDues = new ArrayList<>();
    public static ArrayList courses = new ArrayList();
    public static ArrayList units = new ArrayList();
    public static ArrayList<Courses> myCourse = new ArrayList<>();
    public static DueDateComparator dueDateComparator = new DueDateComparator();

    /**
     * Defualt constructor for user
     */

    public user(String ID) {

        this.UID = ID;



    }

    class myValueEventListener implements ValueEventListener{
            public myValueEventListener(){
                super();
            }
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                college = snapshot.child("college").getValue().toString();
                Log.v("College", college);
                graduateDate = snapshot.child("graduateDate").getValue().toString();
                Log.v("graduateDate", graduateDate);
                major = snapshot.child("major").getValue().toString();
                Log.v("major", major);
                Log.v("UID", UID);
                myCourse = new ArrayList<>();
                courses = new ArrayList();
                for(DataSnapshot course: snapshot.child("courses").getChildren()){
                    Courses temp = course.getValue(Courses.class);
                    myCourse.add(temp);
                    courses.add(temp.getCourseId());
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            System.out.println("The read failed: " + firebaseError.getMessage());
            }


    }



    /**
     *
     * @param fullName
     * @param major
     * @param college
     * @param password
     * @param graduateDate
     */

    public user(String fullName, String major, String college, String password, String graduateDate, String email,String UID){
        this.email = email;
        this.fullName = fullName;
        this.major = major;
        this.college = college;
        this.password = password;
        this.graduateDate = graduateDate;

        //Courses temp = new Courses();

        this.UID = UID;
        Courses temp = new Courses();

        this.myCourse = new ArrayList<>();
        //myCourse.add(temp);
    }


    /**
     * getters
     * @return
     */
    public String getFullName() {return fullName;}
    public String getMajor() {return major;}
    public String getUID() {return this.UID;}
    public String getCollege() {return this.college;}
    public String getPassword(){return this.password;}
    public Courses getCourses(int i) {
        return this.myCourse.get(i);
    }
    public ArrayList getCourses() {
        return this.myCourse;
    }
    public String getGraduateDate(){return graduateDate;}
    public static ArrayList<IndividualAssignment> getRecentDues() {
        return recentDues;
    }

    public String getEmail(){return this.email;}


    public static void setRecentDues(ArrayList<IndividualAssignment> recentDues) {
        user.recentDues = recentDues;
    }

    public static void addUnit(int n){
        unit += n;
    }

    public static void update(){
        double unitObtained = 0.0;
        for(int i = 0; i < myCourse.size(); ++i){
            Courses temp = myCourse.get(i);
            if(temp.getLetter()){
                unitObtained = temp.getUnit() * temp.getGpa();
            }
        }
        gpa = unitObtained/unit;

    }

    static class DueDateComparator implements Comparator<IndividualAssignment> {

        @Override
        public int compare(IndividualAssignment a1, IndividualAssignment a2){
            if(a2.getYear() < a1.getYear()){
                return 1;
            }
            else if(a2.getMonth() < a1.getMonth()){
                return 1;
            }
            else if(a2.getDay() < a1.getDay()){
                return 1;
            }
            else
                return -1;

        }

    }


}


