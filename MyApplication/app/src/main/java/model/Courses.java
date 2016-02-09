package model;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ui.AddCourse;
import ui.BaseActivity;
import androidstudio.edbud.com.myapplication.R;

/**
 * Created by LunaLu on 2/3/16.
 */

public class Courses {

    private ArrayList weightsList, assignmentList;
    private double unit;
    private boolean isLetter;
    private String courseId;
    private double gpa;
    private Map <String, ArrayList<IndividualAssignment>> allAssignments;
    private Map <String, Integer> gradingDistribution;

    /**
     * Default constructor
     */

    public Courses(){
        this.unit = 0.0;
        this.isLetter = true;
        this.courseId = "";
        this.gpa = 0.0;
    }

    /**
     * Three-parameters constructor
     * @param id -- String, course id
     * @param u -- int, course unit
     * @param l -- boolean, true if letter, false if pass no pass
     * @param w -- ArrayList, weights
     * @param p -- ArrayList, percentage of each weight
     */

    public Courses(String id, int u, boolean l, ArrayList w, ArrayList p){
        this.courseId = id;
        this.unit = u;
        this.isLetter = l;
        this.weightsList = w;
        this.gradingDistribution = new HashMap<>();
        this.allAssignments = new HashMap<>();
        for(int i = 0; i < weightsList.size(); ++i){
            allAssignments.put(weightsList.get(i).toString(), new ArrayList<IndividualAssignment>());
            gradingDistribution.put(weightsList.get(i).toString(), Integer.parseInt(p.get(i).toString()));
        }
    }


    /**
     * Add a new small assignment into grading distributions
     * @param weight --  to which this assignment belongs to
     * @param assignment -- assignment name
     * @param y -- due date, year
     * @param m -- due date, month
     * @param d -- due date, day
     */

    public void addAssignments(String weight, String assignment, int y,int m, int d){
        ArrayList temp = allAssignments.get(weight);
        temp.add(new IndividualAssignment(assignment, y, m, d ));
        assignmentList.add(assignment);

    }


    /**
     * Getters
     */

    public double getUnit (){return this.unit;}
    public double getGpa () {return this.gpa;}
    public boolean getLetter () {return this.isLetter;}
    public String getCourseId () {return this.courseId;}


    /**
     *
     *
     * @return  An ArrayList containing all the grading distributions.
     */
    public ArrayList getWeights(){
        return weightsList;
    }

    /**
     *
     * @return An ArrayList containing all the small assignments for displaying in individual course page
     */

    public ArrayList getAssignments(){
        return assignmentList;
    }




}

