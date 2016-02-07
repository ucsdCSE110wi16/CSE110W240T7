package model;

/**
 * Created by Tianqi on 2/7/2016.
 */
public class user {
    private String name;
    private String major;
    private String college;


    public user(){}

    public user(String name, String major, String college){
        this.name = name;
        this.major = major;
        this.college = college;
    }

    public String getName() {return this.name;}
    public String getMajor() {return this.major;}
    public String getCollege() {return this.college;}
}
