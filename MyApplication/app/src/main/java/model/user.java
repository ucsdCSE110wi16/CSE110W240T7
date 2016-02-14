package model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

/**
 * Created by Tianqi on 2/7/2016.
 */

public class user {

    private String email;
    private String fullName;
    private String major;
    private String college;
    private String password;
    private String graduateDate;

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
        courses = new ArrayList();
        units = new ArrayList();
        myCourse = new ArrayList<>();
        this.UID = ID;
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
    public String getFullName() {return this.fullName;}
    public String getMajor() {return this.major;}
    public String getUID() {return this.UID;}
    public String getCollege() {return this.college;}
    public String getPassword(){return this.password;}
    public ArrayList getCourses() {
        return this.myCourse;
    }
    public String getGraduateDate(){return this.graduateDate;}
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
