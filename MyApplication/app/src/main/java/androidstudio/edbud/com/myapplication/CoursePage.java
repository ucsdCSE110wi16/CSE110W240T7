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

import java.util.ArrayList;

public class CoursePage extends BaseActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    public static ArrayList courses = new ArrayList();
    public static ArrayList units = new ArrayList();
    public static ArrayList<courses> myCourse = new ArrayList<courses>();
    private ListView coursesListView;
    private ArrayAdapter arrayAdapter;
    private Context context;
    public static String course_chosen;
    public static int p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_course_page);
        super.onCreateNavigation();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);


        coursesListView = (ListView) findViewById(R.id.lsCourses);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, courses);
        coursesListView.setAdapter(arrayAdapter);
        coursesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
                p = position;
               // course_chosen=coursesListView.getSelectedItem().toString();
                startActivity(new Intent(context, IndividualCourse.class));

            }
        });
    }

    public void onClick(View view) {
        startActivity(new Intent(this, AddCourse.class));
    }



}
