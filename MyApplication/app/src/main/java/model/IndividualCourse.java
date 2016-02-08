package model;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ui.AddAssignment;
import ui.BaseActivity;
import androidstudio.edbud.com.myapplication.R;

public class IndividualCourse extends BaseActivity implements View.OnClickListener{
    private TextView course;
    private TextView unit;
    private TextView letter;
    private ArrayList<String> hws;
    private Context context;
    private ListView assignmentList;
    private ArrayAdapter arrayAdapter;
    private Courses mycourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_course);
        mycourse = Courses.CoursePage.myCourse.get(Courses.CoursePage.p);
        this.setViews();

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.individual_fab);
        fab2.setOnClickListener(this);

        assignmentList = (ListView) findViewById(R.id.expandableListView);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Courses.CoursePage.myCourse.get(Courses.CoursePage.p).getAssignments("hw"));
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
        course.setText(mycourse.courseId);
        unit.setText(Integer.toString(mycourse.unit));
        if(mycourse.letter)
            letter.setText("Letter grade");
        else
            letter.setText("PNP");

    }

}
