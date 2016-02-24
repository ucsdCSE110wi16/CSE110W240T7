package ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ProgressBar;

import com.fasterxml.jackson.databind.deser.Deserializers;

import java.text.DecimalFormat;

import androidstudio.edbud.com.myapplication.R;
import model.Courses;
import model.IndividualAssignment;
import model.IndividualCourse;
import model.User;
import ui.BaseActivity;

public class Homepage extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DecimalFormat df = new DecimalFormat("#.##");
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi);
        context = this;

        super.onCreateNavigation();
        Double gpa = BaseActivity.initialize.getGpa()*10.0;
        ProgressBar progress_bar = (ProgressBar)findViewById(R.id.circle_progress_bar);
        progress_bar.setProgress(gpa.intValue());
        TextView gpanumber = (TextView)findViewById(R.id.GPAnumber);
        gpanumber.setText(df.format(gpa/10.00));
        ListView recentDueList = (ListView) findViewById(R.id.list_homepage);
        HomepageListAdapter adapter = new HomepageListAdapter(this, BaseActivity.initialize.getRecentDues());
        recentDueList.setAdapter(adapter);
        recentDueList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
                String courseId = BaseActivity.initialize.getRecentDues().get(position).getBelongsTo();
                CoursePage.p = BaseActivity.initialize.getTerm(BaseActivity.initialize.getCurrTerm()).getTermCourseList().indexOf(courseId);
                startActivity(new Intent(context, IndividualCourse.class));

            }
        });
    }

    @Override
    public void onBackPressed(){
        // DO NOTHING
    }


}

