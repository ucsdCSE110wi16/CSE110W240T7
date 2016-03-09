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
    private Comparator<IndividualAssignment> dueDateComparator = new DueDateComparator();

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

        for(int i = 0; i < assignments.size(); ++i){
            if(assignments.get(i).getAssignmentName().equals(name))
                return false;
        }

        IndividualAssignment temp = new IndividualAssignment(course, categoryName, name, y, m, d);
        assignments.add(temp);
        Collections.sort(assignments, dueDateComparator);
        BaseActivity.initialize.addRecentDues(temp);
            return true;

    }

    public void addAssignmentScore(String name, double rawScore, double scoreOutOf){
        for(int i = 0; i <assignments.size(); ++i){
            if(assignments.get(i).getAssignmentName().equals(name))
                addAssignmentScore(i, rawScore, scoreOutOf);
        }
    }

    public void addAssignmentScore(int index, double rawScore, double scoreOutOf){
        //remove assignments from recent due
        if(!assignments.get(index).isSetScore())
            BaseActivity.initialize.removeRecentDues(assignments.get(index));

        //set assignment score
        assignments.get(index).setScore(rawScore, scoreOutOf);

        //sort new assignment arraylist
        Collections.sort(assignments, dueDateComparator);

        ////System.out.println("test comparator print after adding score");
        //for(int i = 0; i < assignments.size(); ++i){
        //  //System.out.println(assignments.get(i).getAssignmentName());
        //}

        //update score
        ArrayList<IndividualAssignment> temp = new ArrayList<>();
        for(int i = 0; i < assignments.size(); ++i)
            if(assignments.get(i).isSetScore())
                temp.add(assignments.get(i));
        Collections.sort(temp, myComparator);
        scoreInputted = true;

        //Update percent obtained inside this category

        double newPercent = 0.0;

        if(numToDrop >= temp.size()){
            newPercent = 1.0;
        }
        else{
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
    *Projection stuff
    */

    public Double calculateHighestPossiblePercent(){
        if(assignments.size() == 0){
            return 1.0;
        }
        double newPercent = 0;
        for(int i = 0; i < assignments.size(); ++i){
            if(assignments.get(i).isSetScore())
                newPercent += assignments.get(i).getPercent();
            else
                newPercent += 1;
        }
        newPercent = newPercent/assignments.size();
        //System.out.println(categoryName + " getHighestPossiblePercent: " + newPercent);
        return newPercent;
    }

    public void makeProjection(double percentToObtain, boolean canReachNextLevel, String nextLevel){
        //System.out.println(categoryName + " has percent To Obtain: " + percentToObtain);
        double percentToImprove = 1.0;
        if(percentToObtain > this.totalWeight){
            percentToImprove = 1.0;
        }
        else{
            int num = 0;
            double sum = 0.0;
            for(int i = 0; i < assignments.size(); ++i){
                if(assignments.get(i).isSetScore())
                    sum += assignments.get(i).getPercent();
                else 
                    num += 1;
            }
            if(num >= 1)
                percentToImprove = (percentToObtain/totalWeight * assignments.size() - sum)/num;

            if(percentToImprove > 1)
                percentToImprove = 1.0;
            else if(percentToImprove < 0){
                percentToImprove = 0.0;
            }
        }
        for(int i = 0; i < assignments.size(); ++i){
                if(!assignments.get(i).isSetScore()) {
                    assignments.get(i).setPercentToImprove(percentToImprove);
                    assignments.get(i).setCanReachNextLevel(canReachNextLevel);
                    assignments.get(i).setNextLevel(nextLevel);
                }
        }

        ////System.out.println("percentToObtain: " + percentToObtain);
        ////System.out.println("percentToImprove: " + percentToImprove);
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

    class DueDateComparator implements Comparator<IndividualAssignment>{

        @Override
        public int compare(IndividualAssignment a1, IndividualAssignment a2){
            ////System.out.println("a1 is: " + a1.getAssignmentName() + " a2 is: " + a2.getAssignmentName());
            if((a1.isSetScore() && a2.isSetScore()) || ((!a1.isSetScore()) && (!a2.isSetScore()))){
                int a1Date = a1.getYear() * 10000 + a1.getMonth() * 100 + a1.getDay();
                int a2Date = a2.getYear() * 10000 + a2.getMonth() * 100 + a2.getDay();
                if(a1Date < a2Date)
                    return -1;
                else if(a1Date > a2Date)
                    return 1;
                else
                    return (a1.getAssignmentName().compareTo(a2.getAssignmentName()));
            }
            else if(a1.isSetScore()){
                return 1;
            }
            else{
                return -1;
            }

        }

    }

    class ScoreComparator implements Comparator<IndividualAssignment>{
        @Override
        public int compare(IndividualAssignment lhs, IndividualAssignment rhs) {
            if(lhs.getPercent() > rhs.getPercent())
                return 1;
            else
                return -1;
        }
    }


