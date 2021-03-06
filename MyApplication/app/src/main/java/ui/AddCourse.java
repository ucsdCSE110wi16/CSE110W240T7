package ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.ArrayList;

import androidstudio.edbud.com.myapplication.R;
import model.Courses;


public class AddCourse extends AppCompatActivity implements View.OnClickListener{

    private Button bAddCourse, bAddWeights;
    private EditText etcourseID, etcourseUnit, etWeightID, etWeightPercent;
    private TextView switchStatus;
    private Switch gradeSwitch;
    private boolean letter = true;
    private ListView weightList;
    private ArrayList<String> weights;
    private ArrayList<Integer> percentages;
    private FrameLayout layout_main;
    private PopupWindow popup;
    WeightListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        this.findViewById();


        layout_main.getForeground().setAlpha(0);

        bAddCourse.setOnClickListener(this);
        bAddWeights.setOnClickListener(this);

        weights = new ArrayList<>();
        percentages = new ArrayList<>();

        adapter = new WeightListAdapter(this, weights, percentages);
        weightList.setAdapter(adapter);



        gradeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    switchStatus.setText("Pass/No Pass");
                    letter = false;
                }else{
                    switchStatus.setText("Letter grade");
                    letter = true;
                }
            }
        });

        if(gradeSwitch.isChecked()){
            switchStatus.setText("Pass/No Pass");
            letter = false;
        }
        else {
            switchStatus.setText("Letter grade");
            letter = true;
        }
    }

    /*
    *   find all views
     */
    private void findViewById(){
        layout_main = (FrameLayout) findViewById( R.id.addCourse);
        bAddCourse = (Button) findViewById(R.id.bAddCourse);
        bAddWeights = (Button) findViewById(R.id.bAddWeights);
        etcourseID = (EditText) findViewById(R.id.etCourseID);
        etcourseUnit = (EditText) findViewById(R.id.etUnit);
        weightList= (ListView) findViewById(R.id.list_weights);
        gradeSwitch = (Switch) findViewById(R.id.gradeSwitch);
        switchStatus= (TextView) findViewById(R.id.switchStatus);

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
                Courses courseToAdd = new Courses(courseID, courseUnit, letter, weights, percentages);
                BaseActivity.initialize.getTerm(BaseActivity.initialize.getCurrTerm()).addTermCourses(courseToAdd);
                startActivity(new Intent(this, CoursePage.class));
                break;
            case R.id.bAddWeights:
                showPop(this);
                break;
            case R.id.bAddIndividualWeight:
                String weightID = etWeightID.getText().toString();
                String weightPercent = etWeightPercent.getText().toString();
                if(TextUtils.isEmpty(weightID)){
                    Toast.makeText(this,"Please input weight name",Toast.LENGTH_LONG).show();
                    return;
                }
                else if(TextUtils.isEmpty(weightPercent)){
                    Toast.makeText(this,"Please input weight percent", Toast.LENGTH_LONG).show();
                    return;
                }
                int percent = Integer.parseInt(weightPercent);
                if(weights.indexOf(weightID) != -1){
                    Toast.makeText(this,"This weight has already been added", Toast.LENGTH_LONG).show();
                    return;
                }
                weights.add(weightID);
                percentages.add(percent);
                adapter.notifyDataSetChanged();
                layout_main.getForeground().setAlpha(0);
                popup.dismiss();
                break;
            case R.id.bCancelAddIndividualWeight:
                layout_main.getForeground().setAlpha(0);
                popup.dismiss();
                break;


        }
    }

    public void showPop(Activity context){
        // Inflate the popup_layout.xml
       // RelativeLayout viewGroup = (RelativeLayout) context.findViewById(R.id.addWeights);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_add_weights, null);

        // Creating the PopupWindow
        popup = new PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popup.setFocusable(true);
        popup.setOutsideTouchable(false);

        etWeightID= (EditText) layout.findViewById(R.id.etWeightId);
        etWeightPercent = (EditText) layout.findViewById(R.id.etWeightPercent);

        //Dim the background
        layout_main.getForeground().setAlpha(220);


        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.CENTER, 0, 0);


        // Getting a reference to Close button, and close the popup when clicked.
        Button close = (Button) layout.findViewById(R.id.bAddIndividualWeight);
        Button cancel = (Button) layout.findViewById(R.id.bCancelAddIndividualWeight);
        cancel.setOnClickListener(this);
        close.setOnClickListener(this);

    }



}
