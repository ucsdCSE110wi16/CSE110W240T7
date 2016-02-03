package androidstudio.edbud.com.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class IndividualCourse extends AppCompatActivity implements View.OnClickListener{
    private TextView course;
    private TextView unit;
    private TextView letter;
    private ArrayList<String> hws;
    private ListView assignmentList;
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        course= (TextView) findViewById(R.id.individual_course);
        unit = (TextView) findViewById(R.id.individual_unit);
        course.setText(CoursePage.courses.get(CoursePage.p).toString());
        unit.setText(CoursePage.units.get(CoursePage.p).toString());

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.individual_fab);
        fab2.setOnClickListener(this);


        assignmentList = (ListView) findViewById(R.id.listview_assignments);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, CoursePage.myCourse.get(CoursePage.p).getAssignments());
        assignmentList.setAdapter(arrayAdapter);

    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(this, AddAssignment.class));
    }

}
