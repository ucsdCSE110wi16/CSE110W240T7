package model;

import android.util.Log;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

import Constant.Constant;

/**
 * Created by Tianqi on 2/7/2016.
 */

public class user {

    public static String email;
    public  String fullName;
    public  String major;
    public  String college;
    public  static String password;
    public  String graduateDate;

    private static int unit = 0;
    private static double gpa = 4.0;

    @JsonIgnore
    public static String UID;

    public static ArrayList courses = new ArrayList();
    public static ArrayList units = new ArrayList();
    public static ArrayList<Courses> myCourse = new ArrayList<>();

    /**
     * Defualt constructor for user
     */

    public user(String ID){

        this.UID = ID;
        Firebase ref = new Firebase("https://edbud.firebaseio.com/userInfo/" + user.UID);
        ref.addValueEventListener(new myValueEventListener());

        //Log.v("College", college);
        //Log.v("graduateDate", this.graduateDate);
        //Log.v("major", this.major);


        CourseListAdapter adapter = new CourseListAdapter(Constant.DBURLszh);


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
    public ArrayList getCourses() {
        return this.myCourse;
    }
    public String getGraduateDate(){return graduateDate;}
    public String getEmail(){return this.email;}

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


}
