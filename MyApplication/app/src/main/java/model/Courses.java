package model;


import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import Constant.Constant;

/**
 * Created by LunaLu on 2/3/16.
 */

public class Courses {

    ArrayList<String> weightsList;
    double unit;
    boolean isLetter;
    boolean pass;
    String courseId;
    double gpa,totalPrecent;
    HashMap <String, ArrayList<IndividualAssignment>> allAssignments;
    HashMap <String, Double> gpaThreshold;
    HashMap <String, Category> categories;
    Comparator<IndividualAssignment> myComparator = new ScoreComparator();

    /**
     * Default constructor
     */

    public Courses(){
        this.unit = 0.0;
        this.isLetter = true;
        this.courseId = "";
        this.gpa = 0.0;
        this.pass = true;
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
        this.isLetter = l;
        this.pass = true;
        this.weightsList = w;
        this.categories = new HashMap<>();
        this.allAssignments = new HashMap<>();
        this.gpaThreshold = new HashMap<>();


        for(int i = 0; i < weightsList.size(); ++i){

            allAssignments.put(weightsList.get(i).toString(), new ArrayList<IndividualAssignment>());
            categories.put(weightsList.get(i).toString(), new Category(weightsList.get(i).toString(), Integer.parseInt(p.get(i).toString()), 0));
        }

        //Initialize grade scale
        if(isLetter){
            setGpaThreshold(90.0,80.0,70.0,60.0);
            this.gpa = 4.0;
        }
        else
            setGpaThreshold(60.0);

        this.totalPrecent = 100;


    }

    public void setGpaThreshold(double a, double b, double c, double d){
        gpaThreshold.put("A",a);
        gpaThreshold.put("B",b);
        gpaThreshold.put("C",c);
        gpaThreshold.put("D",d);
    }

    public void setGpaThreshold(double pass){
        gpaThreshold.put("P",pass);

    }

    public void setNumToDrop(String w, int n){
        categories.get(w).setNumToDrop(n);
    }

    public boolean addWeight(String weight, int p){
        if(weightsList.indexOf(weight) != -1)
            return false;
        weightsList.add(weight);
        categories.put(weight, new Category(weight, p, 0));
        allAssignments.put(weight, new ArrayList<IndividualAssignment>());
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

    public void addAssignment(String weight, String assignment, int y,int m, int d){
        ArrayList<IndividualAssignment> temp = allAssignments.get(weight);

        /*
        for(int i = 0; i < temp.size(); i++){
            System.err.print("inside arraylist:  ");
            System.err.println(temp.get(i));
        }
        System.err.println("Not ENTERED");*/
        IndividualAssignment assignmentToAdd = new IndividualAssignment(assignment, y, m, d);
        temp.add(assignmentToAdd);
        //Firebase start = new Firebase(Constant.DBURL);
        //Firebase userAssignments = start.child("userInfo").child("Lihui Lu").child("courses").child(courseId).child("categories").child(weight).child(assignment);
        //userAssignments.setValue(assignmentToAdd);
        //assignmentList.add(assignment);

    }

    public void addAssignmentScore(String weight, int index, double rawScore, double scoreOutOf ){
        ArrayList<IndividualAssignment> temp = allAssignments.get(weight);

        temp.get(index).setScore(rawScore, scoreOutOf);

        Collections.sort(temp, myComparator);

        Category currCategory = categories.get(weight);


        //Update percent obtained inside this category
        int numToDrop = currCategory.getNumToDrop();

        double newPercent = 0.0;
        if(numToDrop >= temp.size()){
            newPercent = 1.0;
        }
        else{
            for(int i = 0; i < temp.size() - numToDrop; ++i){
                newPercent += temp.get(i).getPercent();
            }
            newPercent = newPercent/(temp.size() - numToDrop);
        }

        currCategory.setCurrPercent(newPercent);
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
                percentage += value.getCurrPercent();
                totalWeight +=value.getTotalWeight();
            }
        }
        percentage = (percentage/totalWeight)*100;
        //System.out.println("percentage now: "+ percentage);

        if(isLetter){
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
    public boolean getLetter () {return this.isLetter;}
    public String getCourseId () {return this.courseId;}


    /**
     *
     *
     * @return  An ArrayList containing all the grading distributions.
     */
    public ArrayList<String> getWeights(){
        return weightsList;
    }

    public HashMap<String, ArrayList<IndividualAssignment>> getAllAssignments() {
        return allAssignments;
    }

    public HashMap<String, Category> getCategories(){
        return categories;
    }

    public double getTotalPrecent(){
        return totalPrecent;
    }


    /**Custom ArrayList comparator, put IndividualAssignments with higher scores in front
     *
     */
    class ScoreComparator implements Comparator<IndividualAssignment>{

        @Override
        public int compare(IndividualAssignment a1, IndividualAssignment a2){
            if(a2.getPercent() > a1.getPercent()){
                return 1;
            }
            else
                return -1;
        }

    }







}








