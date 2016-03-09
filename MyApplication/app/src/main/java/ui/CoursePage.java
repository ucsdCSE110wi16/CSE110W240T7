package ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.logging.Handler;

import androidstudio.edbud.com.myapplication.R;
import model.IndividualCourse;

public class CoursePage extends BaseActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private ListView coursesListView;
    private ArrayAdapter arrayAdapter;
    private Context context;
    public static String course_chosen;
    public static int p;
    private CourseListAdapter courseListAdapter;
    private PopupWindow popup;
    private EditText etSetTerm;
    private RelativeLayout layout_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_course_page);
        super.onCreateNavigation();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);


        layout_main = (RelativeLayout) findViewById( R.id.coursePage);
        layout_main.getForeground().setAlpha(0);



        coursesListView = (ListView) findViewById(R.id.lsCourses);
        //coursesListView.addHeaderView(new View(this));
        //coursesListView.addFooterView(new View(this));


        courseListAdapter = new CourseListAdapter(getApplicationContext(),
                BaseActivity.initialize.getTerm(BaseActivity.initialize.getCurrTerm()).getTermCourses());
        coursesListView.setAdapter(courseListAdapter);


        //arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, User.courses);
        //coursesListView.setAdapter(arrayAdapter);
        coursesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
                p = position;
               // course_chosen=coursesListView.getSelectedItem().toString();
                startActivity(new Intent(context, IndividualCourse.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        });
    }

    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.fab:
            startActivity(new Intent(this, AddCourse.class));
                break;
        }
    }





}
