package ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidstudio.edbud.com.myapplication.R;
import model.IndividualCourse;
import model.user;

public class CoursePage extends BaseActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

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
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, user.courses);
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
