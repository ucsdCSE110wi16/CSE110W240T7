package model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import ui.AddAssignment;
import ui.BaseActivity;
import androidstudio.edbud.com.myapplication.R;
import ui.CoursePage;

public class IndividualCourse extends Activity implements View.OnClickListener{
    private TextView course, unit, letter,gpa;
    private ArrayList<String> hws;
    private Context context;
    private ListView assignmentList;
    private ArrayAdapter arrayAdapter;
    private Courses mycourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_course);
        mycourse = CoursePage.myCourse.get(CoursePage.p);
        this.setViews();
        this.setTitle(mycourse.courseId);

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.individual_fab);
        fab2.setOnClickListener(this);

        assignmentList = (ListView) findViewById(R.id.expandableListView);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, CoursePage.myCourse.get(CoursePage.p).getAssignments());
        assignmentList.setAdapter(arrayAdapter);

    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(this, AddAssignment.class));
    }

    private void setViews(){
        course = (TextView) findViewById(R.id.individual_course);
        unit = (TextView) findViewById(R.id.individual_unit);
        letter = (TextView) findViewById(R.id.individual_letter);
        gpa = (TextView) findViewById(R.id.GPA);
        course.setText(mycourse.courseId);
        unit.setText(Double.toString(mycourse.getUnit()));
        if(mycourse.isLetter) {
            letter.setText("Letter grade");
            gpa.setText("4.0");
        }
        else
            letter.setText("PNP");

    }

}
