package ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

import androidstudio.edbud.com.myapplication.R;
import model.Courses;
import model.Term;

import static androidstudio.edbud.com.myapplication.R.id.bpopupCancelAddTermCourse;

/**
 * Created by LunaLu on 2/23/16.
 */
public class AddNewTerm extends AppCompatActivity implements View.OnClickListener {

    private Button bAddTermCourses, bAddTerm;
    private Button bA, bB, bC, bD,bF;
    private EditText etTermName, etTermCourseId,etTermCourseUnit;
    private ArrayList<Courses> coursesArrayList = new ArrayList<>();
    private ListView courseList;
    private FrameLayout layout_main;
    private PopupWindow popup;
    private AddTermListAdapter myAdapter;
    private double unit = 0.0;
    private int courseUnit = 0;
    private TextView termUnit, termGpa, switchStatus;
    private Switch gradeSwitch;
    private boolean letter = true;
    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_term);
        this.findViewById();


        layout_main.getForeground().setAlpha(0);
        myAdapter = new AddTermListAdapter(this, coursesArrayList);
        courseList.setAdapter(myAdapter);

    }

    /*
    *   find all views
     */
    private void findViewById(){
        layout_main = (FrameLayout) findViewById( R.id.addNewTerm);
        etTermName = (EditText) findViewById(R.id.etNewTermID);
        termUnit = (TextView) findViewById(R.id.add_new_term_termUnit);
        bAddTerm = (Button) findViewById(R.id.bAddNewTerm);
        bAddTerm.setOnClickListener(this);
        bAddTermCourses = (Button) findViewById(R.id.bAddNewTermCourses);
        bAddTermCourses.setOnClickListener(this);
        courseList = (ListView) findViewById(R.id.list_add_new_term_courses);
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.bAddNewTerm:
                String termId = etTermName.getText().toString();
                if(TextUtils.isEmpty(termId)){
                    Toast.makeText(this,"Please input term name",Toast.LENGTH_LONG).show();
                    return;
                }

                Term temp = new Term(termId, unit, 0.0, coursesArrayList);
                BaseActivity.initialize.addNewTerm(temp);
                startActivity(new Intent(this, FourYearPlan.class));
                break;
            case R.id.bAddNewTermCourses:
                showPop(this);
                break;
            case R.id.bpopupAddTermCourse:
                String courseId = etTermCourseId.getText().toString();
                String courseUnitString = etTermCourseUnit.getText().toString();
                if(TextUtils.isEmpty(courseId)){
                    Toast.makeText(this,"Please input course name",Toast.LENGTH_LONG).show();
                    return;
                }
                else if(TextUtils.isEmpty(courseUnitString)){
                    Toast.makeText(this,"Please input course unit",Toast.LENGTH_LONG).show();
                    return;
                }
                
                courseUnit = Integer.parseInt(courseUnitString);
                coursesArrayList.add(new Courses(courseId,courseUnit,letter,4.0));

                unit += courseUnit;
                termUnit.setText(df.format(unit));
                layout_main.getForeground().setAlpha(0);
                popup.dismiss();
                myAdapter.notifyDataSetChanged();
                break;
            case bpopupCancelAddTermCourse:
                layout_main.getForeground().setAlpha(0);
                popup.dismiss();
                break;
            

        }
    }

    public void showPop(Activity context){
        // Inflate the popup_layout.xml
        // RelativeLayout viewGroup = (RelativeLayout) context.findViewById(R.id.addWeights);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_add_termcourse, null);

        // Creating the PopupWindow
        popup = new PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popup.setFocusable(true);
        popup.setOutsideTouchable(false);

        etTermCourseId= (EditText) layout.findViewById(R.id.etTermCourseName);
        etTermCourseUnit = (EditText) layout.findViewById(R.id.etTermCourseUnit);
        TextView textGradeTitle = (TextView) layout.findViewById(R.id.TermCourseGradeTitle);
        textGradeTitle.setVisibility(View.GONE);

        //Dim the background
        layout_main.getForeground().setAlpha(220);

        bA = (Button) layout.findViewById(R.id.bA);
        bB = (Button) layout.findViewById(R.id.bB);
        bC = (Button) layout.findViewById(R.id.bC);
        bD = (Button) layout.findViewById(R.id.bD);
        bF = (Button) layout.findViewById(R.id.bF);
        bA.setVisibility(View.GONE);
        bB.setVisibility(View.GONE);
        bC.setVisibility(View.GONE);
        bD.setVisibility(View.GONE);
        bF.setVisibility(View.GONE);

        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.CENTER, 0, 0);


        // Getting a reference to Close button, and close the popup when clicked.
        Button close = (Button) layout.findViewById(R.id.bpopupAddTermCourse);
        Button cancel = (Button) layout.findViewById(bpopupCancelAddTermCourse);
        cancel.setOnClickListener(this);
        close.setOnClickListener(this);
        gradeSwitch = (Switch) layout.findViewById(R.id.letterSwitch);
        switchStatus = (TextView) layout.findViewById(R.id.letterSwitchStatus);

        gradeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    switchStatus.setText("Pass/No Pass");
                    letter = false;
                } else {
                    switchStatus.setText("Letter grade");
                    letter = true;
                }
            }
        });

    }

}
