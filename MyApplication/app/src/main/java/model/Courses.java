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
    String highestGradePossible, grade;
    double gpa,totalPercent,rawPercent, totalSettedWeight;
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
            this.setGpaThresholdLetter(90.0,80.0,70.0,60.0,97.0,93.0,87.0,83.0,77.0,73.0);
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
            this.setGpaThresholdLetter(90.0,80.0,70.0,60.0,97.0,93.0,87.0,83.0,77.0,73.0);
            this.gpa = 4.0;
            highestGradePossible = "A+";
            grade = "A+";
        }
        else{
            this.setGpaThresholdPNP(60.0);
            highestGradePossible = "P";
            grade = "P";
        }

        this.totalPercent = 100;
        this.rawPercent = 0.0;


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
        this.gpaThreshold.put("P", pass);

    }

    public void setNumToDrop(String w, int n){
        categories.get(w).setNumToDrop(n);
    }

    public void setWeightsList(ArrayList<String> list){
        this.weightsList = list;
    }


    public void setHighestGradePossible(String highestGradePossible) {
        this.highestGradePossible = highestGradePossible;
    }

    public void setGrade(String grade){
        this.grade = grade;
    }

    public void setTotalSettedWeight(double totalSettedWeight) {
        this.totalSettedWeight = totalSettedWeight;
    }

    public void setRawPercent(double rawPercent) {
        this.rawPercent = rawPercent;
    }

    public void setTotalPercent(double totalPercent) {
        this.totalPercent = totalPercent;
    }



    public boolean addWeight(String weight, int p){
        if(this.weightsList == null){
            this.weightsList = new ArrayList<>();
        }
        if(this.weightsList.indexOf(weight) != -1)
            return false;
        this.weightsList.add(weight);
        this.categories.put(weight, new Category(weight, p, 0));
        updatehighestGradePossible();
        makeProjection();
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
        updatehighestGradePossible();
        makeProjection();
        Firebase start = new Firebase("https://edbud.firebaseio.com/userInfo/" + BaseActivity.initialize.uid);
        start.setValue(BaseActivity.initialize);
        return true;
    }

    public void addAssignmentScore(String weight, int index, double rawScore, double scoreOutOf ){
        this.categories.get(weight).addAssignmentScore(index, rawScore, scoreOutOf);
        this.totalPercent = updateScores();
        updatehighestGradePossible();
        //System.out.println("highestGradePossible: " + highestGradePossible);
        makeProjection();
        BaseActivity.initialize.update();
    }

    /**Update Scores, recalculate percentage, and update the new gpa or pass/nopass status
     *
     */
    public double updateScores(){
        double percentage = 0.0;
        double totalWeight = 0.0;
        for(Category value : categories.values()){
            if(value.isScoreInputted()){
                ////System.out.println(value.getCategoryName() + "\ncurr percent:" + value.getCurrPercent() + "\nout of: " + value.getTotalWeight());
                percentage += value.getCurrPercent()*value.getTotalWeight();
                totalWeight +=value.getTotalWeight();
            }
        }
        totalSettedWeight = totalWeight;
        rawPercent = percentage;
        percentage = (percentage/totalWeight)*100;
        ////System.out.println("percentage now: "+ percentage);

        if(letter){
            if(percentage >= gpaThreshold.get("A")){
                gpa = 4.0;
                grade = "A";
            }
            else if(percentage >= gpaThreshold.get("A-")) {
                gpa = 3.7;
                grade = "A-";
            }
            else if(percentage >= gpaThreshold.get("B+")) {
                gpa = 3.3;
                grade = "B+";
            }
            else if(percentage >= gpaThreshold.get("B")) {
                gpa = 3.0;
                grade = "B";
            }
            else if(percentage >= gpaThreshold.get("B-")) {
                gpa = 2.7;
                grade = "B-";
            }
            else if(percentage >= gpaThreshold.get("C+")) {
                gpa = 2.3;
                grade = "C+";
            }
            else if(percentage >= gpaThreshold.get("C")) {
                gpa = 2.0;
                grade = "C";
            }
            else if(percentage >= gpaThreshold.get("C-")) {
                gpa = 1.7;
                grade = "C-";
            }
            else if(percentage >= gpaThreshold.get("D")) {
                gpa = 1.0;
                grade = "D";
            }
            else{
                gpa = 0.0;
                grade = "F";
            }
        }
        else{
            if(percentage < gpaThreshold.get("P")){
                pass = false;
                grade = "NP";
            }
            else{
                pass = true;
                grade = "P";
            }
        }
        return percentage;

    }




    /**
    *Projection stuff
    */


    /**
    *Update highest possible grade this course can obtain
    */

    public void updatehighestGradePossible(){
        double newPercent = 0.0;
        for(Category value : categories.values()){
            newPercent += value.calculateHighestPossiblePercent() * value.getTotalWeight();
           // //System.out.println("category: " + value.getCategoryName());
           // //System.out.println("getHighestPossiblePercent: " + value.getHighestPossiblePercent());
        }

        if(letter){
            if(newPercent >= gpaThreshold.get("A+")){
                highestGradePossible = "A+";
            }
            else if(newPercent >= gpaThreshold.get("A")){
                highestGradePossible = "A";
            }
            else if(newPercent >= gpaThreshold.get("A-")) {
                highestGradePossible = "A-";
            }
            else if(newPercent >= gpaThreshold.get("B+")) {
                highestGradePossible = "B+";
            }
            else if(newPercent >= gpaThreshold.get("B")) {
                highestGradePossible = "B";
            }
            else if(newPercent >= gpaThreshold.get("B-")) {
                highestGradePossible = "B-";
            }
            else if(newPercent >= gpaThreshold.get("C+")) {
                highestGradePossible = "C+";
            }
            else if(newPercent >= gpaThreshold.get("C")) {
                highestGradePossible = "C";
            }
            else if(newPercent >= gpaThreshold.get("C-")) {
                highestGradePossible = "C-";
            }
            else if(newPercent >= gpaThreshold.get("D")) {
                highestGradePossible = "D";
            }
            else
                highestGradePossible = "F";
        }
        else{
            if(newPercent < gpaThreshold.get("P")){
                highestGradePossible = "NP";
            }
            else
                highestGradePossible = "P";
        }


    }




    public void makeProjection(){

        //find out the next level of grade user needs to reach
        boolean canReachNextLevel = true;
        double percentToObtain;
        String nextLevel;
        if(totalPercent >= gpaThreshold.get("A")){
            percentToObtain = gpaThreshold.get("A");
            nextLevel = "A";
            canReachNextLevel = false;
        }
        else if(totalPercent >= gpaThreshold.get("B")){
            if(gpaThreshold.get(highestGradePossible) <= gpaThreshold.get("B+")){
                percentToObtain = gpaThreshold.get("B");
                nextLevel = "B";
                canReachNextLevel = false;
            }
            else{
                percentToObtain = gpaThreshold.get("A");
                nextLevel = "A";
                canReachNextLevel = true;
            }
        }
        else if(totalPercent >= gpaThreshold.get("C")){
             if(gpaThreshold.get(highestGradePossible) <= gpaThreshold.get("C+")){
                percentToObtain = gpaThreshold.get("C");
                 nextLevel = "C";
                canReachNextLevel = false; 
            }
            else{
                percentToObtain = gpaThreshold.get("B");
                 nextLevel = "B";
                canReachNextLevel = true;
            }
        }
        else if(totalPercent >= gpaThreshold.get("D")){
             if(gpaThreshold.get(highestGradePossible) <= gpaThreshold.get("C-")){
                percentToObtain = gpaThreshold.get("D");
                 nextLevel = "D";
                canReachNextLevel = false; 
            }
            else{
                percentToObtain = gpaThreshold.get("C");
                 nextLevel = "C";
                canReachNextLevel = true;
            }
        }
        else{
             if(gpaThreshold.get(highestGradePossible) <= gpaThreshold.get("F")){
                percentToObtain = gpaThreshold.get("F");
                 nextLevel = "F";
                canReachNextLevel = false; 
            }
            else{
                percentToObtain = gpaThreshold.get("D");
                 nextLevel = "D";
                canReachNextLevel = true;
            }
        }
        
        //System.out.println("percentToObtain: " + percentToObtain);
        //make projection for every unsetted individual assignments in each category
        //System.out.println("total setted weight: " + totalSettedWeight);
        for(Category value : categories.values()){
           // //System.out.println("rawPercent: " + rawPercent);
           // //System.out.println("this category: " + value.getCategoryName() + "need to get: " + (percentToObtain/100*totalSettedWeight - (rawPercent - value.getCurrPercent()*value.getTotalWeight())));
            if(totalSettedWeight == 0.0)
                value.makeProjection(percentToObtain/100*value.getTotalWeight(),canReachNextLevel, nextLevel);
            else if(value.isScoreInputted())
                value.makeProjection(percentToObtain/100*totalSettedWeight - (rawPercent - value.getCurrPercent()*value.getTotalWeight()), canReachNextLevel, nextLevel);
            else
                value.makeProjection(percentToObtain/100*(totalSettedWeight + value.getTotalWeight()) - rawPercent, canReachNextLevel,nextLevel);
        }


    }









    /*
     * Getters
     */
    public double getUnit (){return this.unit;}
    public double getGpa () {return this.gpa;}
    public String getGrade(){return this.grade;}
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

    public double getRawPercent() {
        return rawPercent;
    }

    public double getTotalSettedWeight() {
        return totalSettedWeight;
    }

    public String getHighestGradePossible() {
        return highestGradePossible;
    }

    public ArrayList<String> getWeightsList(){
        if(weightsList == null)
            weightsList = new ArrayList<>();
        return this.weightsList;
    }


}








