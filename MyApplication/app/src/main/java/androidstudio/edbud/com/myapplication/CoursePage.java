package androidstudio.edbud.com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class CoursePage extends AppCompatActivity implements View.OnClickListener{
    public static ArrayList courses = new ArrayList();
    private ListView coursesListView;
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);



        coursesListView = (ListView) findViewById(R.id.lsCourses);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, courses);
        coursesListView.setAdapter(arrayAdapter);
    }


    @Override
    public void onClick(View view) {
        startActivity(new Intent(this, AddCourse.class));
    }

}
