package model;

import java.util.ArrayList;

/**
 * Created by LunaLu on 2/19/16.
 */
public class Term {
    String termName;
    double termGpa=0.0;
    ArrayList<Courses> termCourses=new ArrayList<>();
    boolean termPassed=false;
    double termUnit=0.0;

    Term(){

    }

    Term(String name, boolean passed, double unit, double gpa){
        this.termName=name;
        this.termPassed = passed;
        this.termUnit = unit;
        this.termGpa = gpa;
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

    public void setTermUnit(double unit){
        this.termUnit = unit;
    }

    public boolean addTermCourses(Courses course){
        if(this.termCourses != null){
            if(termCourses.contains(course))
            return false;
        }
        this.termCourses.add(course);
        return true;
    }

    public void addTermUnit(double unit){
        this.termUnit += unit;
    }


}
