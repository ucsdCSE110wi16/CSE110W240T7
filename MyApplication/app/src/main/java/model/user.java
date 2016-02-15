package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

import java.util.ArrayList;

import Constant.Constant;

/**
 * Created by Tianqi on 2/7/2016.
 */

public class user {

    private String email;
    private String fullName;
    private static String major;
    public static String college;
    private String password;
    private static String graduateDate;

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
        Firebase userinfo = new Firebase("https://edbud.firebaseio.com/userInfo/" + user.UID);

        this.college = userinfo.child("college").toString();
        this.graduateDate = userinfo.child("graduateDate").toString();
        this.fullName = userinfo.child("fullName").toString();
        CourseListAdapter adapter = new CourseListAdapter(Constant.DBURLszh);


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
    public static String getMajor() {return major;}
    public String getUID() {return UID;}
    public static String getCollege() {return college;}
    public String getPassword(){return password;}
    public static Courses getCourse(int i) {
        return myCourse.get(i);
    }
    public static String getGraduateDate(){return graduateDate;}
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
