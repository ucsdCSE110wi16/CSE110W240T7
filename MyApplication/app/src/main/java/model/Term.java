package model;

import com.firebase.client.Firebase;

import java.util.ArrayList;

import ui.BaseActivity;

/**
 * Created by LunaLu on 2/19/16.
 */
public class Term {
    String termName;
    double termGpa=4.0;
    ArrayList<Courses> termCourses=new ArrayList<>();
    ArrayList<String> termCourseList = new ArrayList<>();
    double termUnit=0.0;
    double termLetterUnit = 0.0;
    double termObtainedUnit = 0.0;

    Term(){

    }

    Term(String name, double unit, double gpa){
        this.termName=name;
        this.termUnit = unit;
        this.termGpa = gpa;
    }

    public Term(String name, double unit, double gpa, ArrayList<Courses> course) {
        this.termName=name;
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


    public double getTermUnit(){
        return termUnit;
    }

    public double getTermLetterUnit() {
        return termLetterUnit;
    }

    public double getTermObtainedUnit() {
        return termObtainedUnit;
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


    public void setTermCourses(ArrayList<Courses> termCourses) {
        this.termCourses = termCourses;
    }

    public void setTermLetterUnit(double termLetterUnit) {
        this.termLetterUnit = termLetterUnit;
    }

    public void setTermObtainedUnit(double termObtainedUnit) {
        this.termObtainedUnit = termObtainedUnit;
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
            termObtainedUnit += course.getUnit() * course.getGpa();
            setTermGpa(termObtainedUnit/termLetterUnit);
        }
        //TODO:modify term gpa
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
