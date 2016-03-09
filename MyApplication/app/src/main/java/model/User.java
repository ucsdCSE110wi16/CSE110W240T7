package model;


import com.firebase.client.Firebase;

import java.util.ArrayList;
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
    private String currTerm;
    private int unit;
    private double gpa;
    public String uid;
    private ArrayList<IndividualAssignment> recentDues = new ArrayList<>();
    private ArrayList<String> recentDueIds = new ArrayList<>();
    private ArrayList<IndividualAssignment> recentDueToShow = new ArrayList<>();
    private DueDateComparator dueDateComparator = new DueDateComparator();
    private LinkedHashMap<String, Term> my4YearPlan = new LinkedHashMap<>();
    private LinkedHashMap<String, Term> myPastTerms = new LinkedHashMap<>();
    private LinkedHashMap<String, Term> myFutureTerms = new LinkedHashMap<>();
    private ArrayList<String> terms = new ArrayList<>();
    private ArrayList<String> pastTerms = new ArrayList<>();
    private ArrayList<String> futureTerms = new ArrayList<>();



    /**
     * Default constructor for firebase
     */

    public User(){

    }

    public User(User copy){

        //user info
        this.fullName = copy.getFullName();
        this.major = copy.getMajor();
        this.uid = copy.getUid();
        this.college = copy.getCollege();
        this.password = copy.getPassword();
        this.email = copy.getEmail();

        //grade info
        this.gpa = copy.getGpa();
        this.unit = copy.getUnit();

        //current quarter stuff
        this.currTerm = copy.getCurrTerm();
        this.recentDues = copy.getRecentDues();
        this.recentDueIds = copy.getRecentDueIds();
        this.recentDueToShow = copy.getRecentDueToShow();

        //4 year plan
        this.my4YearPlan = copy.getMy4YearPlan();
        this.myFutureTerms=copy.getMyFutureTerms();
        this.myPastTerms=copy.getMyPastTerms();
        this.terms = copy.getTerms();
        this.futureTerms = copy.getFutureTerms();
        this.pastTerms = copy.getPastTerms();
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
     */

    public User(String fullName, String major, String college, String password, String email, String UID, String term, double gpa, int unit){

        //user info
        this.email = email;
        this.fullName = fullName;
        this.major = major;
        this.college = college;
        this.password = password;
        this.uid = UID;
        this.unit = unit;
        this.gpa = gpa;

        //current term stuff
        this.currTerm = term;


        //4 year plan
        this.my4YearPlan.put(term, new Term(term, 0.0, 4.0));
        this.myFutureTerms.put(term, new Term(term,0.0, 4.0));
        this.terms.add(term);
        this.futureTerms.add(term);
    }


    /**
     * getters for firebase
     *
     */
    public String getFullName() {return fullName;}
    public String getMajor() {return major;}
    public String getUid() {
        return uid;
    }
    public String getCollege() {return college;}
    public String getPassword(){return password;}
    public String getEmail(){return email;}
    public double getGpa() {return gpa;}
    public int getUnit() {
        return unit;
    }
    public LinkedHashMap<String, Term> getMy4YearPlan() {
        return my4YearPlan;
    }
    public LinkedHashMap<String, Term> getMyFutureTerms() {
        return myFutureTerms;
    }
    public LinkedHashMap<String, Term> getMyPastTerms() {
        return myPastTerms;
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
    public ArrayList<String> getRecentDueIds() {
        return recentDueIds;
    }
    public ArrayList<IndividualAssignment> getRecentDueToShow() {
        return recentDueToShow;
    }
    public Term getTerm(String term){
        if(myFutureTerms.containsKey(term))
            return myFutureTerms.get(term);
        else
            return myPastTerms.get(term);
    }
    public ArrayList<String> getTerms() {
        return terms;
    }
    public ArrayList<String> getFutureTerms() {
        return futureTerms;
    }
    public ArrayList<String> getPastTerms() {
        return pastTerms;
    }

    /**
     * Setters for firebase
     */
    public void setRecentDues(ArrayList<IndividualAssignment> recentDues) {
        this.recentDues = recentDues;}
    public void setRecentDueIds(ArrayList<String> recentDueIds) {
        this.recentDueIds = recentDueIds;
    }
    public void setRecentDueToShow(ArrayList<IndividualAssignment> recentDueToShow) {
        this.recentDueToShow = recentDueToShow;
    }
    public void setTerms(ArrayList<String> terms) {
        this.terms = terms;
    }
    public void setCurrTerm(String term) {
        this.currTerm = term;
    }
    public void setMy4YearPlan(LinkedHashMap<String, Term> my4YearPlan) {
        this.my4YearPlan = my4YearPlan;
    }
    public void setMyFutureTerms(LinkedHashMap<String, Term> myFutureTerms) {
        this.myFutureTerms = myFutureTerms;
    }
    public void setMyPastTerms(LinkedHashMap<String, Term> myPastTerms) {
        this.myPastTerms = myPastTerms;
    }
    /**
    *Update current quarter unit
    */


    public void update(){
        ArrayList<Courses> temp = myFutureTerms.get(currTerm).getTermCourses();
        double unitObtained = 0.0;
        for(int i = 0; i < temp.size(); ++i) {
            Courses course = temp.get(i);

            if (course.getLetter()) {
                unitObtained += course.getUnit() * course.getGpa();
                System.out.println(unitObtained);
            }
        }
            System.out.println(myFutureTerms.get(currTerm).getTermLetterUnit());
        System.out.println(unitObtained / myFutureTerms.get(currTerm).getTermLetterUnit());
        this.myFutureTerms.get(currTerm).setTermGpa(unitObtained / myFutureTerms.get(currTerm).getTermLetterUnit());

       /* unitObtained = 0.0;
        for(Term term:my4YearPlan.values()){
            unitObtained += term.getTermGpa() * term.getTermLetterUnit();
        }
        gpa = unitObtained/letterUnit;*/


    }

    public boolean addRecentDues(IndividualAssignment assignment){
        if(recentDueIds.indexOf(assignment.getAssignmentName()) != -1)
            return false;
        recentDues.add(assignment);
        recentDueToShow.add(assignment);
        recentDueIds.add(assignment.getAssignmentName());

        //Collections.sort(recentDueToShow, this.dueDateComparator);
        return true;
    }

    public boolean removeRecentDues(IndividualAssignment assignment){
        //IndividualAssignment toDelete = recentDues.get(recentDueIds.indexOf(assignment.getAssignmentName()));
        for(int i = 0; i <recentDueToShow.size(); ++i){
            if(recentDueToShow.get(i).getAssignmentName().equals(assignment.getAssignmentName()) &&
                    recentDueToShow.get(i).getBelongsToCourse().equals(assignment.getBelongsToCourse()) &&
                    recentDueToShow.get(i).getBelongsToCategory().equals(assignment.getBelongsToCategory())){
                    recentDueToShow.remove(i);
            }
        }
        //recentDues.remove(toDelete);
        //recentDueIds.remove(assignment.getAssignmentName());
        Firebase start = new Firebase("https://edbud.firebaseio.com/userInfo/").child(BaseActivity.initialize.uid);
        start.setValue(BaseActivity.initialize);
        return true;
    }

    public boolean addNewTerm(Term term){
        if(this.myFutureTerms.get(term.getTermName()) != null){
            return false;
        }
        this.futureTerms.add(term.getTermName());
        this.myFutureTerms.put(term.getTermName(), term);
        Firebase start = new Firebase("https://edbud.firebaseio.com/userInfo/").child(BaseActivity.initialize.uid);
        start.setValue(BaseActivity.initialize);
        return true;
    }

    public boolean addPastTerm(Term term){
        if(this.myPastTerms.get(term.getTermName()) != null){
            return false;
        }
        this.pastTerms.add(term.getTermName());
        this.myPastTerms.put(term.getTermName(), term);
        Firebase start = new Firebase("https://edbud.firebaseio.com/userInfo/").child(BaseActivity.initialize.uid);
        start.setValue(BaseActivity.initialize);
        return true;

    }

   class DueDateComparator implements Comparator<IndividualAssignment> {

        @Override
        public int compare(IndividualAssignment a1, IndividualAssignment a2){
            int a1Date = a1.getYear() * 10000 + a1.getMonth() * 100 + a1.getDay();
            int a2Date = a2.getYear() * 10000 + a2.getMonth() * 100 + a2.getDay();
            if(a1Date < a2Date)
                return -1;
            else if(a1Date > a2Date)
                return 1;
            else
                return (a1.getAssignmentName().compareTo(a2.getAssignmentName()));

        }

    }


}


