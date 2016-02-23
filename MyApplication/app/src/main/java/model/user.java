package model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;

import ui.BaseActivity;

/**
 * Created by Tianqi on 2/7/2016.
 */
public class User {

    private String email;
    private String fullName;
    private String major;
    private String college;
    private String password;
    private String graduateDate;
    private String currTerm;
    private boolean isTermSet;
    private int unit;
    private double gpa;
    public String uid;
    private ArrayList<IndividualAssignment> recentDues = new ArrayList<>();
    private ArrayList<String> terms = new ArrayList<>();
    private DueDateComparator dueDateComparator = new DueDateComparator();
    private LinkedHashMap<String, Term> my4YearPlan = new LinkedHashMap<>();


    /**
     * Default constructor for firebase
     */

    public User(){

    }

    /**
     * Default constructor for User
     */


    public User(String ID) {
        this.uid = ID;
    }


    /**
     *
     * @param fullName
     * @param major
     * @param college
     * @param password
     * @param graduateDate
     */

    public User(String fullName, String major, String college, String password, String graduateDate, String email, String UID){
        this.email = email;
        this.fullName = fullName;
        this.major = major;
        this.college = college;
        this.password = password;
        this.graduateDate = graduateDate;
        this.uid = UID;
        this.currTerm = "current quarter";
        this.isTermSet = false;
        this.unit = 0;
        this.gpa = 4.0;
    }

    /**
     * getters
     *
     */
    public String getFullName() {return fullName;}
    public String getMajor() {return major;}
    public String getUID() {return uid;}
    public String getCollege() {return college;}
    public String getPassword(){return password;}
    public String getGraduateDate(){return graduateDate;}
    public String getEmail(){return email;}
    public boolean getIsTermSet() {return isTermSet;}
    public double getGpa() {return gpa;}

    public LinkedHashMap<String, Term> getMy4YearPlan() {
        return my4YearPlan;
    }

    public String getCurrTerm(){
        return currTerm;
    }


    public ArrayList<IndividualAssignment> getRecentDues() {
        if(recentDues.isEmpty()){
            recentDues = new ArrayList<>();
        }
        return recentDues;
    }


    public Term getTerm(String term){
        if(my4YearPlan.get(term) == null){
            my4YearPlan.put(term, new Term(term, false, 0.0, 0.0));
        }
        return my4YearPlan.get(term);
    }

    public ArrayList<String> getTerms() {
        return terms;
    }



    /**
     * Setters
     */
    public void setRecentDues(ArrayList<IndividualAssignment> recentDues) {
        this.recentDues = recentDues;}

    public void setTerms(ArrayList<String> terms) {
        this.terms = terms;
    }


    public void setIsTermSet(boolean termSetted) {
        this.isTermSet = termSetted;
    }

    public void setCurrTerm(String term) {
        if(!isTermSet){
            this.my4YearPlan.remove(this.currTerm);
            this.my4YearPlan.put(term, new Term(term, false, 0.0, 0.0));
            terms.remove(this.currTerm);
            this.terms.add(term);
        }
        this.isTermSet = true;
        Firebase start = new Firebase("https://edbud.firebaseio.com/userInfo/" + BaseActivity.initialize.uid);
        start.setValue(BaseActivity.initialize);
    }



    public void setMy4YearPlan(LinkedHashMap<String, Term> my4YearPlan) {
        this.my4YearPlan = my4YearPlan;
    }

    /**
    *Update current quarter unit
    */


    public void update(){
        ArrayList<Courses> temp = my4YearPlan.get(currTerm).getTermCourses();
        double unitObtained = 0.0;
        for(int i = 0; i < temp.size(); ++i){
            Courses course = temp.get(i);
            if(course.getLetter()){
                unitObtained = course.getUnit() * course.getGpa();
            }
        }

        unitObtained = 0.0;
        my4YearPlan.get(currTerm).setTermGpa(unitObtained/unit);


        for(Term term:my4YearPlan.values()){
            unitObtained += term.getTermGpa() * term.getTermUnit();
        }
        gpa = unitObtained/unit;


    }

    public boolean addRecentDues(IndividualAssignment a){
        if(recentDues.indexOf(a) != -1)
            return false;
        recentDues.add(a);
        Collections.sort(recentDues, this.dueDateComparator);
        return true;
    }

    public boolean removeRecentDues(IndividualAssignment a){
        if(recentDues.indexOf(a) != -1)
            return false;
        recentDues.remove(a);
        return true;
    }

    public boolean addCourse(Courses course){
        if(my4YearPlan.isEmpty()){
            my4YearPlan.put(currTerm, new Term(currTerm, false, 0.0, 0.0));
        }
        if(!BaseActivity.initialize.my4YearPlan.get(currTerm).addTermCourses(course)){
            return false;
        }
        unit += course.getUnit();
        BaseActivity.initialize.my4YearPlan.get(currTerm).addTermUnit(course.getUnit());
        Firebase start = new Firebase("https://edbud.firebaseio.com/userInfo/").child(BaseActivity.initialize.uid);
        start.setValue(BaseActivity.initialize);
        return true;
    }

   class DueDateComparator implements Comparator<IndividualAssignment> {

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


