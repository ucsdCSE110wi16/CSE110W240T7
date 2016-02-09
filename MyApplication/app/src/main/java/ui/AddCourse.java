package ui;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

import androidstudio.edbud.com.myapplication.R;
import model.Courses;


public class AddCourse extends AppCompatActivity implements View.OnClickListener{

    private Button bAddCourse, bAddWeights;
    private EditText etcourseID, etcourseUnit, etWeightID, etWeightPercent;
    private Switch gradeSwitch;
    private boolean letter;
    private ListView weightList;
    private ArrayList weights = new ArrayList();
    private ArrayList percentages = new ArrayList();
    private FrameLayout layout_main;
    private PopupWindow popup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        this.findViewById();


        layout_main.getForeground().setAlpha(0);

        bAddCourse.setOnClickListener(this);
        bAddWeights.setOnClickListener(this);

        WeightListAdapter adapter = new WeightListAdapter(this, weights, percentages);
        weightList.setAdapter(adapter);


        gradeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                letter = false;

            }
        });
    }

    public void findViewById(){
        layout_main = (FrameLayout) findViewById( R.id.addCourse);
        bAddCourse = (Button) findViewById(R.id.bAddCourse);
        bAddWeights = (Button) findViewById(R.id.bAddWeights);
        etcourseID = (EditText) findViewById(R.id.etCourseID);
        etcourseUnit = (EditText) findViewById(R.id.etUnit);
        weightList= (ListView) findViewById(R.id.list_weights);
        gradeSwitch = (Switch) findViewById(R.id.gradeSwitch);
        etWeightID = (EditText) findViewById(R.id.etWeightId);
        etWeightPercent = (EditText) findViewById(R.id.etWeightPercent);

    }


    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.bAddCourse:
                String courseID = etcourseID.getText().toString();
                String unit = etcourseUnit.getText().toString();

                if (TextUtils.isEmpty(courseID)) {
                    etcourseID.setError("Please input course name");
                    return;
                } else if (TextUtils.isEmpty(unit)) {
                    etcourseUnit.setError("Please input course unit");
                    return;
                }
                int courseUnit = Integer.parseInt(unit);
                Courses.CoursePage.myCourse.add(new Courses(courseID, courseUnit, letter));
                Courses.CoursePage.courses.add(courseID);
                Courses.CoursePage.units.add(courseUnit);
                startActivity(new Intent(this, Courses.CoursePage.class));
                break;
            case R.id.bAddWeights:
                showPop(this);
                break;
            case R.id.bAddIndividualWeight:
                layout_main.getForeground().setAlpha(0);
                String weightID = etWeightID.getText().toString();
                String weightPercent = etWeightPercent.getText().toString();
                if(TextUtils.isEmpty(weightID)){
                    etWeightID.setError("Please input weight name");
                }
                else if(TextUtils.isEmpty(weightPercent)){
                    etWeightPercent.setError("Please input weight percent");
                }
                popup.dismiss();
                break;

        }
    }

    public void showPop(Activity context){


        // Inflate the popup_layout.xml
        RelativeLayout viewGroup = (RelativeLayout) context.findViewById(R.id.addWeights);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.activity_add_weights, null);

        // Creating the PopupWindow
        popup = new PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popup.setFocusable(true);


        layout_main.getForeground().setAlpha(220);

        // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.
       //int OFFSET_X = 30;
        //int OFFSET_Y = 30;


        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.CENTER, 0, 0);


        // Getting a reference to Close button, and close the popup when clicked.
        Button close = (Button) layout.findViewById(R.id.bAddIndividualWeight);
        close.setOnClickListener(this);

    }



}
