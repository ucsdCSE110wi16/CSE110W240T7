package model;

/**
 * Created by Tianqi on 2/7/2016.
 */
public class User {

    private String fullName;
    private String major;
    private String college;
    private String password;
    private String graduateDate;

    /**
     * Defualt constructor for user
     */
    public User(){}

    /**
     *
     * @param fullName
     * @param major
     * @param college
     * @param password
     * @param graduateDate
     */

    public User(String fullName, String major, String college, String password, String graduateDate){
        this.fullName = fullName;
        this.major = major;
        this.college = college;
        this.password = password;
        this.graduateDate = graduateDate;
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

}
