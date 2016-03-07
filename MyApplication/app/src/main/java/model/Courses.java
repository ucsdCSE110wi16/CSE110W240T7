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
    double gpa,totalPercent;
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
            this.setGpaThresholdLetter(93.0,83.0,73.0,63.0,97.0,90.0,87.0,80.0,77.0,70.0);
            this.gpa = 4.0;
        }
        else
            this.setGpaThresholdPNP(60.0);

    }

    public Courses(String id, int u, boolean l, double grade){
        this.courseId = id;
        this.unit = u;
        this.letter = l;
        this.gpa = grade;
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
            this.setGpaThresholdLetter(93.0,83.0,73.0,63.0,97.0,90.0,87.0,80.0,77.0,70.0);
            this.gpa = 4.0;
        }
        else
            this.setGpaThresholdPNP(60.0);

        this.totalPercent = 100;


    }

    public void setGpaThreshold(LinkedHashMap<String, Double> threshold){
        this.gpaThreshold = threshold;
    }

    public void setGpaThresholdLetter(double a, double b, double c, double d,double ap, double am,
                                      double bp, double bm, double cp, double cm){
        this.gpaThreshold.put("A",a);
        this.gpaThreshold.put("B",b);
        this.gpaThreshold.put("C",c);
        this.gpaThreshold.put("D",d);
        this.gpaThreshold.put("A+",ap);
        this.gpaThreshold.put("A-",am);
        this.gpaThreshold.put("B+",bp);
        this.gpaThreshold.put("B-",bm);
        this.gpaThreshold.put("C+",cp);
        this.gpaThreshold.put("C-",cm);
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
        if(this.weightsList == null){
            this.weightsList = new ArrayList<>();
        }
        if(this.weightsList.indexOf(weight) != -1)
            return false;
        this.weightsList.add(weight);
        this.categories.put(weight, new Category(weight, p, 0));
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
        if(!this.categories.get(weight).addAssignment(courseId,assignment,y,m,d))
            return false;
        Firebase start = new Firebase("https://edbud.firebaseio.com/userInfo/" + BaseActivity.initialize.uid);
        start.setValue(BaseActivity.initialize);
        return true;
    }

    public double addAssignmentScore(String weight, int index, double rawScore, double scoreOutOf ){
        this.categories.get(weight).addAssignmentScore(index, rawScore, scoreOutOf);
        updateScores();
        BaseActivity.initialize.update();
        return gpa;
    }

    /**Update Scores, recalculate percentage, and update the new gpa or pass/nopass status
     *
     */
    public void updateScores(){
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
            else if(percentage >= gpaThreshold.get("A-")) {
                gpa = 3.7;
            }
            else if(percentage >= gpaThreshold.get("B+")) {
                gpa = 3.3;
            }
            else if(percentage >= gpaThreshold.get("B")) {
                gpa = 3.0;
            }
            else if(percentage >= gpaThreshold.get("B-")) {
                gpa = 2.7;
            }
            else if(percentage >= gpaThreshold.get("C+")) {
                gpa = 2.3;
            }

            else if(percentage >= gpaThreshold.get("C")) {
                gpa = 2.0;
            }
            else if(percentage >= gpaThreshold.get("C-")) {
                gpa = 1.7;
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
        this.totalPercent =  percentage;

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
        return this.gpaThreshold;
    }


    public LinkedHashMap<String, Category> getCategories(){
        return this.categories;
    }

    public double getTotalPercent(){
        return this.totalPercent;
    }

    public ArrayList<String> getWeightsList(){
        if(weightsList == null)
            weightsList = new ArrayList<>();
        return this.weightsList;
    }

}








