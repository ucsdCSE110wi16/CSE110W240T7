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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.deser.Deserializers;

import java.text.DecimalFormat;
import java.util.ArrayList;

import androidstudio.edbud.com.myapplication.R;
import model.Courses;
import model.Term;

/**
 * Created by LunaLu on 2/23/16.
 */
public class AddTerm extends AppCompatActivity implements View.OnClickListener {

    private Button bAddTermCourses, bAddTerm;
    private Button bA, bB, bC, bD,bF, badd, bsub;
    private EditText etTermName, etTermCourseId, etTermCourseUnit;
    private ArrayList<Courses> coursesArrayList = new ArrayList<>();
    private ListView courseList;
    private FrameLayout layout_main;
    private PopupWindow popup;
    private AddTermListAdapter myAdapter;
    private double courseGpa=-1.0, gpa=4.0, unit = 0.0, letterUnit = 0.0;
    private String courseGrade;
    private int courseUnit = 0;
    private Switch gradeSwitch;
    private boolean letter = true;
    private boolean plus = false;
    private boolean minus = false;
    private TextView switchStatus, termUnit, termGpa;
    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);
        this.findViewById();


        layout_main.getForeground().setAlpha(0);
        myAdapter = new AddTermListAdapter(this, coursesArrayList,false);
        courseList.setAdapter(myAdapter);

    }

    /*
    *   find all views
     */
    private void findViewById(){
        layout_main = (FrameLayout) findViewById( R.id.addTerm);
        etTermName = (EditText) findViewById(R.id.etTermID);
        termUnit = (TextView) findViewById(R.id.add_term_termUnit);
        termGpa = (TextView) findViewById(R.id.add_term_termGpa);
        bAddTerm = (Button) findViewById(R.id.bAddTerm);
        bAddTerm.setOnClickListener(this);
        bAddTermCourses = (Button) findViewById(R.id.bAddTermCourses);
        bAddTermCourses.setOnClickListener(this);
        courseList = (ListView) findViewById(R.id.list_add_term_courses);
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.bAddTerm:
                String termId = etTermName.getText().toString();
                if(TextUtils.isEmpty(termId)){
                    Toast.makeText(this,"Please input term name",Toast.LENGTH_LONG).show();
                    return;
                }
                Term temp = new Term(termId, unit, gpa, coursesArrayList);
                BaseActivity.initialize.addPastTerm(temp);
                startActivity(new Intent(this, FourYearPlan.class));
                break;
            case R.id.bAddTermCourses:
                showPop(this);
                break;
            case R.id.bpopupAddPastCourse:
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
                else if(courseGpa == -1.0){
                    Toast.makeText(this, "Please choose course grade", Toast.LENGTH_LONG).show();
                    return;
                }



                if(courseGpa == 4.3){
                    courseGrade = "A+";
                    courseGpa = 4.0;
                }
                else if(courseGpa == 3.7){
                    courseGrade = "A-";
                }
                else if(courseGpa == 3.3){
                    courseGrade = "B+";
                }
                else if(courseGpa == 2.7){
                    courseGrade = "B-";
                }
                else if(courseGpa == 2.3){
                    courseGrade = "C+";
                }
                else if(courseGpa == 1.7){
                    courseGrade = "C-";
                }
                else if(courseGrade.equals("D")){
                    courseGpa = 1.0;
                }
                else if(courseGrade.equals("F")){
                    courseGpa = 0.0;
                }
                else if(courseGrade.equals("P")){
                    courseGpa = 4.0;
                }
                else if(courseGrade.equals("NP")){
                    courseGpa = 0.0;
                }

                courseUnit = Integer.parseInt(courseUnitString);
                coursesArrayList.add(new Courses(courseId, courseUnit, letter, courseGpa, courseGrade));

                if(letter){
                    gpa = (letterUnit * gpa + courseUnit*courseGpa)/(letterUnit + courseUnit);
                    letterUnit+=courseUnit;
                }

                unit += courseUnit;
                termUnit.setText(df.format(unit));
                termGpa.setText(df.format(gpa));
                layout_main.getForeground().setAlpha(0);
                popup.dismiss();
                myAdapter.notifyDataSetChanged();
                letter = true;
                courseGpa = -1.0;
                break;
            case R.id.bpopupCancelAddPastCourse:
                layout_main.getForeground().setAlpha(0);
                popup.dismiss();
                break;
            case R.id.bA_course:
                courseGpa = 4.0;
                if(letter)
                    courseGrade = "A";
                else
                    courseGrade = "P";
                changeButtonColor("A");
                plus = false;
                minus = false;
                break;
            case R.id.bB_course:
                if(!letter) {
                    courseGpa = 0.0;
                    courseGrade= "NP";
                }
                else {
                    courseGpa = 3.0;
                    courseGrade = "B";
                }
                changeButtonColor("B");
                plus = false;
                minus = false;
                break;
            case R.id.bC_course:
                courseGpa = 2.0;
                courseGrade = "C";
                changeButtonColor("C");
                plus = false;
                minus = false;
                break;
            case R.id.bD_course:
                courseGpa = 1.0;
                courseGrade = "D";
                changeButtonColor("D");
                plus = false;
                minus = false;
                break;
            case R.id.bF_course:
                courseGpa = 0.0;
                courseGrade = "F";
                changeButtonColor("F");
                plus = false;
                minus = false;
                break;
            case R.id.bplus:

                System.out.println("plus clicked");
                if(minus)
                    courseGpa +=0.6;
                else if (!plus)
                    courseGpa += .3;
                changeButtonColor("Plus");
                plus = true;
                break;
            case R.id.bminus:
                System.out.println("minus clicked");
                if(plus)
                    courseGpa -=0.6;
                else if(!minus)
                    courseGpa -=0.3;
                changeButtonColor("Minus");
                minus = true;
                break;



        }
    }

    public void showPop(Activity context){
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_add_course, null);

        // Creating the PopupWindow
        popup = new PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popup.setFocusable(true);
        popup.setOutsideTouchable(false);

        etTermCourseId= (EditText) layout.findViewById(R.id.etPastCourseName);
        etTermCourseUnit = (EditText) layout.findViewById(R.id.etPastCourseUnit);
        bA = (Button) layout.findViewById(R.id.bA_course);
        bB = (Button) layout.findViewById(R.id.bB_course);
        bC = (Button) layout.findViewById(R.id.bC_course);
        bD = (Button) layout.findViewById(R.id.bD_course);
        bF = (Button) layout.findViewById(R.id.bF_course);
        badd = (Button) layout.findViewById(R.id.bplus);
        bsub = (Button) layout.findViewById(R.id.bminus);

        gradeSwitch = (Switch) layout.findViewById(R.id.letterSwitch_course);
        switchStatus = (TextView) layout.findViewById(R.id.letterSwitchStatus_course);
        switchStatus.setText("Letter grade");

            bA.setOnClickListener(this);
            bB.setOnClickListener(this);
            bC.setOnClickListener(this);
            bD.setOnClickListener(this);
            bF.setOnClickListener(this);
            badd.setOnClickListener(this);
            bsub.setOnClickListener(this);

            switchStatus.setText("Letter grade");
            letter = true;

            gradeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        switchStatus.setText("Pass/No Pass");
                        letter = false;
                        bA.setText("P");
                        bB.setText("NP");
                        bC.setVisibility(View.GONE);
                        bD.setVisibility(View.GONE);
                        bF.setVisibility(View.GONE);
                        badd.setVisibility(View.GONE);
                        bsub.setVisibility(View.GONE);
                        courseGpa = -1.0;
                        changeButtonColor("Reset");

                    } else {
                        switchStatus.setText("Letter grade");
                        letter = true;
                        bA.setText("A");
                        bB.setText("B");
                        bC.setVisibility(View.VISIBLE);
                        bD.setVisibility(View.VISIBLE);
                        bF.setVisibility(View.VISIBLE);
                        badd.setVisibility(View.VISIBLE);
                        bsub.setVisibility(View.VISIBLE);
                        courseGpa = -1.0;
                        changeButtonColor("Reset");
                    }
                }
            });


        //Dim the background
        layout_main.getForeground().setAlpha(220);


        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.CENTER, 0, 0);


        // Getting a reference to Close button, and close the popup when clicked.
        Button close = (Button) layout.findViewById(R.id.bpopupAddPastCourse);
        Button cancel = (Button) layout.findViewById(R.id.bpopupCancelAddPastCourse);
        cancel.setOnClickListener(this);
        close.setOnClickListener(this);

    }


    private void changeButtonColor(String button){
        switch(button){
            case "A":
                bA.setBackgroundColor(getColor(R.color.colorPrimaryLight));
                bB.setBackgroundColor(Color.TRANSPARENT);
                bC.setBackgroundColor(Color.TRANSPARENT);
                bD.setBackgroundColor(Color.TRANSPARENT);
                bF.setBackgroundColor(Color.TRANSPARENT);
                badd.setBackgroundColor(Color.TRANSPARENT);
                bsub.setBackgroundColor(Color.TRANSPARENT);
                break;
            case "B":
                bB.setBackgroundColor(getColor(R.color.colorPrimaryLight));
                bA.setBackgroundColor(Color.TRANSPARENT);
                bC.setBackgroundColor(Color.TRANSPARENT);
                bD.setBackgroundColor(Color.TRANSPARENT);
                bF.setBackgroundColor(Color.TRANSPARENT);
                badd.setBackgroundColor(Color.TRANSPARENT);
                bsub.setBackgroundColor(Color.TRANSPARENT);
                break;
            case "C":
                bC.setBackgroundColor(getColor(R.color.colorPrimaryLight));
                bB.setBackgroundColor(Color.TRANSPARENT);
                bA.setBackgroundColor(Color.TRANSPARENT);
                bD.setBackgroundColor(Color.TRANSPARENT);
                bF.setBackgroundColor(Color.TRANSPARENT);
                badd.setBackgroundColor(Color.TRANSPARENT);
                bsub.setBackgroundColor(Color.TRANSPARENT);
                break;
            case "D":
                bD.setBackgroundColor(getColor(R.color.colorPrimaryLight));
                bB.setBackgroundColor(Color.TRANSPARENT);
                bC.setBackgroundColor(Color.TRANSPARENT);
                bA.setBackgroundColor(Color.TRANSPARENT);
                bF.setBackgroundColor(Color.TRANSPARENT);
                badd.setBackgroundColor(Color.TRANSPARENT);
                bsub.setBackgroundColor(Color.TRANSPARENT);
                break;
            case "F":
                bF.setBackgroundColor(getColor(R.color.colorPrimaryLight));
                bB.setBackgroundColor(Color.TRANSPARENT);
                bC.setBackgroundColor(Color.TRANSPARENT);
                bD.setBackgroundColor(Color.TRANSPARENT);
                bA.setBackgroundColor(Color.TRANSPARENT);
                badd.setBackgroundColor(Color.TRANSPARENT);
                bsub.setBackgroundColor(Color.TRANSPARENT);
                break;
            case "Plus":
                badd.setBackgroundColor(getColor(R.color.colorPrimaryLight));
                bsub.setBackgroundColor(Color.TRANSPARENT);
                break;
            case "Minus":
                bsub.setBackgroundColor(getColor(R.color.colorPrimaryLight));
                badd.setBackgroundColor(Color.TRANSPARENT);
                break;
            default:
                bsub.setBackgroundColor(Color.TRANSPARENT);
                badd.setBackgroundColor(Color.TRANSPARENT);
                bF.setBackgroundColor(Color.TRANSPARENT);
                bB.setBackgroundColor(Color.TRANSPARENT);
                bC.setBackgroundColor(Color.TRANSPARENT);
                bD.setBackgroundColor(Color.TRANSPARENT);
                bA.setBackgroundColor(Color.TRANSPARENT);
                break;


        }
    }
}
