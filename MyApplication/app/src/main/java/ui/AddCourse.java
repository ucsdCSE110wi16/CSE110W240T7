package ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;

import java.util.ArrayList;

import androidstudio.edbud.com.myapplication.R;
import model.Courses;


public class AddCourse extends AppCompatActivity implements View.OnClickListener{

    private Button bAddCourse;
    private EditText etcourseID, etcourseUnit;
    private Switch gradeSwitch;
    private boolean letter;
    private ListView weightList;
    private ArrayList weights;
    private ArrayList percentages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        bAddCourse = (Button) findViewById(R.id.bAddCourse);
        bAddCourse.setOnClickListener(this);
        etcourseID = (EditText) findViewById(R.id.etCourseID);
        etcourseUnit = (EditText) findViewById(R.id.etUnit);
        weightList= (ListView) findViewById(R.id.list_weights);
        WeightListAdapter adapter = new WeightListAdapter(this, weights, percentages);
        weightList.setAdapter(adapter);

        gradeSwitch = (Switch) findViewById(R.id.gradeSwitch);
        gradeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                letter = false;

            }
        });
    }


    @Override
    public void onClick(View view){
        String courseID = etcourseID.getText().toString();

        int courseUnit = Integer.parseInt(etcourseUnit.getText().toString());

        Courses.CoursePage.myCourse.add(new Courses(courseID, courseUnit, letter));
        Courses.CoursePage.courses.add(courseID);
        Courses.CoursePage.units.add(courseUnit);
        startActivity(new Intent(this, Courses.CoursePage.class));
    }



}
