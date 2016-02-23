package model;


import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import ui.BaseActivity;

/**
 * Created by LunaLu on 2/3/16.
 */

public class Courses {

    double unit;
    boolean letter;
    boolean pass;
    String courseId;
    double gpa,totalPrecent;
    ArrayList<String> weightsList;
    LinkedHashMap <String, Double> gpaThreshold = new LinkedHashMap<>();
    LinkedHashMap <String, Category> categories = new LinkedHashMap<>();

    /**
     * Default constructor
     */

    public Courses(){
        this.unit = 0.0;
        this.letter = true;
        this.courseId = "";
        this.gpa = 0.0;
        this.pass = true;
        if(letter){
            this.setGpaThresholdLetter(90.0,80.0,70.0,60.0);
            this.gpa = 4.0;
        }
        else
            this.setGpaThresholdPNP(60.0);

    }

    /**
     * Three-parameters constructor
     * @param id -- String, course id
     * @param u -- int, course unit
     * @param l -- boolean, true if letter, false if pass no pass
     * @param w -- ArrayList, weights
     * @param p -- ArrayList, percentage of each weight
     */

    public Courses(String id, int u, boolean l, ArrayList<String> w, ArrayList<Integer> p){

        this.courseId = id;
        this.unit = u;
        this.letter = l;
        this.pass = true;
        this.weightsList = w;
        this.gpaThreshold = new LinkedHashMap<>();
        this.categories = new LinkedHashMap<>();


        for(int i = 0; i < weightsList.size(); ++i){
            categories.put(weightsList.get(i).toString(), new Category(weightsList.get(i).toString(), Integer.parseInt(p.get(i).toString()), 0));
        }

        //Initialize grade scale
        if(letter){
            this.setGpaThresholdLetter(90.0,75.0,60.0,40.0);
            this.gpa = 4.0;
        }
        else
            this.setGpaThresholdPNP(60.0);

        this.totalPrecent = 100;


    }

    public void setGpaThreshold(LinkedHashMap<String, Double> threshold){
        this.gpaThreshold = threshold;
    }

    public void setGpaThresholdLetter(double a, double b, double c, double d){
        this.gpaThreshold.put("A",a);
        this.gpaThreshold.put("B",b);
        this.gpaThreshold.put("C",c);
        this.gpaThreshold.put("D",d);
    }

    public void setGpaThresholdPNP(double pass){
        this.gpaThreshold.put("P",pass);

    }

    public void setNumToDrop(String w, int n){
        categories.get(w).setNumToDrop(n);
    }

    public void setWeightsList(ArrayList<String> list){
        this.weightsList = list;
    }


    public boolean addWeight(String weight, int p){
        if(weightsList.indexOf(weight) != -1)
            return false;
        weightsList.add(weight);
        categories.put(weight, new Category(weight, p, 0));
        return true;
    }
    /**
     * Add a new small assignment into grading distributions
     * @param weight --  to which this assignment belongs to
     * @param assignment -- assignment name
     * @param y -- due date, year
     * @param m -- due date, month
     * @param d -- due date, day
     */

    public boolean addAssignment(String weight, String assignment, int y,int m, int d){
        if(! categories.get(weight).addAssignment(assignment,y,m,d))
            return false;
        Firebase start = new Firebase("https://edbud.firebaseio.com/userInfo/" + BaseActivity.initialize.uid);
        start.child("/my4YearPlan").child(BaseActivity.initialize.getCurrTerm()).child("/courses").child(getCourseId()).setValue(this);
        return true;
    }

    public void addAssignmentScore(String weight, int index, double rawScore, double scoreOutOf ){
        categories.get(weight).addAssignmentScore(index, rawScore, scoreOutOf);
        totalPrecent = updateScores();
    }

    /**Update Scores, recalculate percentage, and update the new gpa or pass/nopass status
     *
     */
    public double updateScores(){
        double percentage = 0.0;
        double totalWeight = 0.0;
        for(Category value : categories.values()){
            if(value.isScoreInputted()){
                //System.out.println(value.getCategoryName() + "\ncurr percent:" + value.getCurrPercent() + "\nout of: " + value.getTotalWeight());
                percentage += value.getCurrPercent()*value.getTotalWeight();
                totalWeight +=value.getTotalWeight();
            }
        }
        percentage = (percentage/totalWeight)*100;
        //System.out.println("percentage now: "+ percentage);

        if(letter){
            if(percentage >= gpaThreshold.get("A")){
                gpa = 4.0;
            }
            else if(percentage >= gpaThreshold.get("B")) {
                gpa = 3.0;
            }
            else if(percentage >= gpaThreshold.get("C")) {
                gpa = 2.0;
            }
            else if(percentage >= gpaThreshold.get("D")) {
                gpa = 1.0;
            }
            else
                gpa = 0.0;
        }
        else{
            if(percentage < gpaThreshold.get("P")){
                pass = false;
            }
        }
        return percentage;

    }


    /**
     * Getters
     */
    public double getUnit (){return this.unit;}
    public double getGpa () {return this.gpa;}
    public boolean getPass(){return this.pass;}
    public boolean getLetter () {return this.letter;}
    public String getCourseId () {return this.courseId;}
    
    public HashMap<String, Double> getGpaThreshold() {
        return gpaThreshold;
    }


    public LinkedHashMap<String, Category> getCategories(){
        return categories;
    }

    public double getTotalPrecent(){
        return totalPrecent;
    }

    public ArrayList<String> getWeightsList(){
        return weightsList;
    }

}








