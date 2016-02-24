package model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ui.BaseActivity;

/**
 * Created by LunaLu on 2/12/16.
 */
public class Category {
    private double totalWeight;
    private int numToDrop;
    private double currPercent;
    private boolean scoreInputted;
    private String categoryName;
    private ArrayList<IndividualAssignment> assignments = new ArrayList<>();
    private Comparator<IndividualAssignment> myComparator = new ScoreComparator();

    Category(){

    }

    Category(String name, double weight, int n){
        this.categoryName = name;
        this.totalWeight = weight;
        this.numToDrop = n;
        this.currPercent = 1.0;
        this.scoreInputted = false;
    }


    /**
     *Setters
     */
    public void setCurrPercent(double p){
        this.scoreInputted = true;
        this.currPercent = p;
    }

    public void setNumToDrop(int n){
        this.numToDrop = n;
    }

    public boolean addAssignment(String course, String name, int y, int m, int d){
        if(assignments == null){
            assignments = new ArrayList<>();
        }
        if(assignments.indexOf(name) != -1){
            return false;
        }
        else{
            IndividualAssignment temp = new IndividualAssignment(course, name, y, m, d);
            assignments.add(temp);
            BaseActivity.initialize.addRecentDues(temp);
            return true;
        }
    }

    public void addAssignmentScore(int index, double rawScore, double scoreOutOf){

        assignments.get(index).setScore(rawScore, scoreOutOf);
        BaseActivity.initialize.removeRecentDues(assignments.get(index));
        IndividualAssignment tempAssignment = assignments.get(index);
        assignments.remove(index);
        assignments.add(tempAssignment);
        ArrayList<IndividualAssignment> temp = new ArrayList<>();
        for(int i = 0; i < assignments.size(); ++i)
            temp.add(assignments.get(i));
        Collections.sort(temp, myComparator);
        scoreInputted = true;

        //Update percent obtained inside this category

        double newPercent = 0.0;

        if(numToDrop >= temp.size()){
            newPercent = 1.0;
        }
        else{
            /*
            for(int i = 0; i < temp.size() - numToDrop; ++i){
                newPercent += temp.get(i).getPercent();
            }
            newPercent = newPercent/(temp.size() - numToDrop);*/
            int i = 0;
            for(; i < temp.size()-numToDrop; ++i){
                if(!temp.get(i).isSetScore()){
                    break;
                }
                else{
                    newPercent += temp.get(i).getPercent();
                }
            }
                newPercent = newPercent/i;
            }

            setCurrPercent(newPercent);

    }

    /**
     *Getters
     */
    public String getCategoryName(){
        return categoryName;
    }

    public double getTotalWeight(){
        return totalWeight;
    }

    public int getNumToDrop(){
        return numToDrop;
    }

    public double getCurrPercent(){
        return currPercent;
    }

    public boolean isScoreInputted(){
        return scoreInputted;
    }

    public ArrayList<IndividualAssignment> getAssignments(){
        return assignments;
    }



}

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


