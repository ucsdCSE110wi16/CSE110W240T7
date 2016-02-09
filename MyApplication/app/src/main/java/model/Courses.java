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

    ArrayList weights;
    double unit;
    boolean isLetter;
    String courseId;
    double gpa;
    ArrayList assignments;

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
     * @param id
     * @param u
     * @param l
     */

    public Courses(String id, int u, boolean l, ArrayList w){
        this.courseId = id;
        this.unit = u;
        this.isLetter = l;
        this.weights = w;
        this.assignments = new ArrayList();
    }

    /**
     * Getters
     */

    public double getUnit (){return this.unit;}
    public double getGpa () {return this.gpa;}
    public boolean getLetter () {return this.isLetter;}
    public String getCourseId () {return this.courseId;}

    public ArrayList getWeights(){
        return weights;
    }

    public ArrayList getAssignments(){
        return assignments;
    }

    public void addAssignments(String hw){
        assignments.add(hw);
    }


}

