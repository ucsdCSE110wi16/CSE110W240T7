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
        mycourse = CoursePage.myCourse.get(CoursePage.p);
        this.setViews();

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.individual_fab);
        fab2.setOnClickListener(this);

        assignmentList = (ListView) findViewById(R.id.expandableListView);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, CoursePage.myCourse.get(CoursePage.p).getAssignments("hw"));
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
