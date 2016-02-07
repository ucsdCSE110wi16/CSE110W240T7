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
    Map<String, String> assignments;
    int unit;
    boolean letter;
    String courseId;
    int gpa;

    Courses(String id, int u, boolean l){
        this.courseId=id;
        this.unit=u;
        this.letter=l;
        this.assignments = new HashMap<>();;
    }

    public void addAssignment(String hw, String weights){
        assignments.put(hw, weights);
    }

    ArrayList getAssignments(String weights){
        return new ArrayList() ;
    }

    public static class CoursePage extends BaseActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
        public static ArrayList courses = new ArrayList();
        public static ArrayList units = new ArrayList();
        public static ArrayList<Courses> myCourse = new ArrayList<>();
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
}
