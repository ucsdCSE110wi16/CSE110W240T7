package ui;

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

import androidstudio.edbud.com.myapplication.R;
import model.IndividualAssignment;
import model.IndividualCourse;
import model.user;
import ui.BaseActivity;

public class Homepage extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi);

        super.onCreateNavigation();
        ProgressBar progress_bar = (ProgressBar)findViewById(R.id.circle_progress_bar);
        progress_bar.setProgress(35);
        TextView gpanumber = (TextView)findViewById(R.id.GPAnumber);
        Double gpa = 3.5;
        String str = String.valueOf(gpa);
        gpanumber.setText(str);
        ListView recentDueList = (ListView) findViewById(R.id.list_homepage);
        HomepageListAdapter adapter = new HomepageListAdapter(this, user.recentDues);
        recentDueList.setAdapter(adapter);
        recentDueList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
               

            }
        });
    }

    @Override
    public void onBackPressed(){
        // DO NOTHING
    }


}

