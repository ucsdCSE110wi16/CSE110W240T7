package model;

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
    private ArrayList<Courses> usrArrayList;

    /**
     * Defualt constructor for user
     */

    public user(){}

    /**
     *
     * @param fullName
     * @param major
     * @param college
     * @param password
     * @param graduateDate
     */

    public user(String fullName, String major, String college, String password, String graduateDate, String email){
        this.email = email;
        this.fullName = fullName;
        this.major = major;
        this.college = college;
        this.password = password;
        this.graduateDate = graduateDate;
        Courses temp = new Courses();
        this.usrArrayList = new ArrayList<Courses>();
        usrArrayList.add(temp);
    }

    /**
     * getters
     * @return
     */

    public String getFullName() {return this.fullName;}
    public String getMajor() {return this.major;}
    public String getCollege() {return this.college;}
    public String getPassword(){return this.password;}
    public String getGraduateDate(){return this.graduateDate;}
    public ArrayList<Courses> getUsrArrayList () {return this.usrArrayList;}
    public String getEmail(){return this.email;}

}
