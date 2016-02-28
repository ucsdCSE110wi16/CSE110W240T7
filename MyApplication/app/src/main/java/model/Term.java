package model;

import com.firebase.client.Firebase;

import java.util.ArrayList;

import ui.BaseActivity;

/**
 * Created by LunaLu on 2/19/16.
 */
public class Term {
    String termName;
    double termGpa=0.0;
    ArrayList<Courses> termCourses=new ArrayList<>();
    ArrayList<String> termCourseList = new ArrayList<>();
    boolean termPassed=false;
    double termUnit=0.0;
    double termLetterUnit = 0.0;

    Term(){

    }

    Term(String name, boolean passed, double unit, double gpa){
        this.termName=name;
        this.termPassed = passed;
        this.termUnit = unit;
        this.termGpa = gpa;
    }

    public Term(String name, boolean passed, double unit, double gpa, ArrayList<Courses> course) {
        this.termName=name;
        this.termPassed = passed;
        this.termUnit = unit;
        this.termGpa = gpa;
        this.termCourses = course;
    }

    /**
     * Getters
     */

    public String getTermName() {
        return termName;
    }

    public ArrayList<Courses> getTermCourses() {
        if(termCourses==null){
            termCourses = new ArrayList<>();
        }
        return termCourses;
    }

    public double getTermGpa() {
        return termGpa;
    }

    public boolean getTermPassed() {
        return termPassed;
    }

    public double getTermUnit(){
        return termUnit;
    }

    public double getTermLetterUnit() {
        return termLetterUnit;
    }

    public ArrayList<String> getTermCourseList() {
        return termCourseList;
    }

    /**
     * Setters
     */

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public void setTermGpa(double termGpa) {
        this.termGpa = termGpa;
    }

    public void setTermPassed(boolean termPassed) {
        this.termPassed = termPassed;
    }

    public void setTermCourses(ArrayList<Courses> termCourses) {
        this.termCourses = termCourses;
    }

    public void setTermLetterUnit(double termLetterUnit) {
        this.termLetterUnit = termLetterUnit;
    }

    public void setTermUnit(double unit){
        this.termUnit = unit;
    }

    public void setTermCourseList(ArrayList<String> termCourseList) {
        this.termCourseList = termCourseList;
    }

    public boolean addTermCourses(Courses course){
        if(this.termCourses != null){
            if(termCourses.contains(course))
            return false;
        }
        if(course.getLetter()){
            termLetterUnit += course.getUnit();
        }
        addTermUnit(course.getUnit());
        this.termCourses.add(course);
        this.termCourseList.add(course.getCourseId());
        Firebase start = new Firebase("https://edbud.firebaseio.com/userInfo/").child(BaseActivity.initialize.uid);
        start.setValue(BaseActivity.initialize);
        return true;
    }

    public void addTermUnit(double unit){
        this.termUnit += unit;
    }


}
