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
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.text.DecimalFormat;

import androidstudio.edbud.com.myapplication.R;
import model.Courses;
import model.IndividualAssignment;
import model.IndividualCourse;
import model.User;
import ui.BaseActivity;

public class Homepage extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,  View.OnClickListener{
    DecimalFormat df = new DecimalFormat("#.##");
    Context context;
    TextView gpanumber, gpaText;
    boolean isCurrGpa = true;
    ProgressBar progress_bar;
    Double gpa, currGpa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Firebase ref = new Firebase("https://edbud.firebaseio.com/userInfo/" + initialize.uid);
        ref.addValueEventListener(new myValueEventListener());
        setContentView(R.layout.activity_navi);
        context  = this;
        super.onCreateNavigation();
        gpanumber = (TextView)findViewById(R.id.GPAnumber);
        gpanumber.setOnClickListener(this);

        gpaText = (TextView) findViewById(R.id.GPAtext);
        gpaText.setText("Term GPA");

    }

    class myValueEventListener implements ValueEventListener {


        public myValueEventListener(){
            super();
        }
        @Override
        public void onDataChange(DataSnapshot snapshot) {

            //headerCollege.setText(snapshot.child("college").getValue().toString());

            //headerMajor.setText(snapshot.child("major").getValue().toString());

            //headerName.setText(snapshot.child("fullName").getValue().toString());

            BaseActivity.initialize = new User(snapshot.getValue(User.class));

            gpa = BaseActivity.initialize.getGpa()*10.0;
            currGpa = BaseActivity.initialize.getTerm(BaseActivity.initialize.getCurrTerm()).getTermGpa()*10.0;
            progress_bar = (ProgressBar)findViewById(R.id.circle_progress_bar);
            progress_bar.setProgress(currGpa.intValue());

            gpanumber.setText(df.format(currGpa/10.0));


            ListView recentDueList = (ListView) findViewById(R.id.list_homepage);
            HomepageListAdapter adapter = new HomepageListAdapter(context, BaseActivity.initialize.getRecentDueToShow());
            recentDueList.setAdapter(adapter);
            recentDueList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        final int position, long id) {
                    String courseId = BaseActivity.initialize.getRecentDueToShow().get(position).getBelongsTo();
                    CoursePage.p = BaseActivity.initialize.getTerm(BaseActivity.initialize.getCurrTerm()).getTermCourseList().indexOf(courseId);
                    startActivity(new Intent(context, IndividualCourse.class));

                }
            });

        }
        @Override
        public void onCancelled(FirebaseError firebaseError) {
            System.out.println("The read failed: " + firebaseError.getMessage());
        }


    }

    @Override
    public void onBackPressed() {
        // DO NOTHING
    }

    @Override
    public void onClick(View v) {
        if(isCurrGpa){
            progress_bar.setProgress(gpa.intValue());
            gpanumber.setText(df.format(gpa/10.00));

            isCurrGpa = false;
            gpaText.setText("Overall GPA");
        }
        else{
            progress_bar.setProgress(currGpa.intValue());
            gpanumber.setText(df.format(currGpa/10.00));
            isCurrGpa = true;
            gpaText.setText("Term GPA");
        }

    }
}

