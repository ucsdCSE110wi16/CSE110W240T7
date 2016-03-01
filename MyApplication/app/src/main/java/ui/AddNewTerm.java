package ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

import androidstudio.edbud.com.myapplication.R;
import model.Courses;
import model.Term;

/**
 * Created by LunaLu on 2/23/16.
 */
public class AddNewTerm extends AppCompatActivity implements View.OnClickListener {

    private Button bAddTermCourses, bAddTerm;
    private EditText etTermName;
    private ArrayList<Courses> coursesArrayList = new ArrayList<>();
    private ListView courseList;
    private FrameLayout layout_main;
    private PopupWindow popup;
    private AddTermListAdapter myAdapter;
    private double unit = 0.0;
    private int courseUnit = 0;
    private TextView termUnit, termGpa;
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
               // showPop(this);
                break;
           /* case R.id.bpopupAddNewTermCourse:
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
                coursesArrayList.add(new Courses(courseId,courseUnit, letter, courseGpa));

                unit += courseUnit;
                termUnit.setText(df.format(unit));
                layout_main.getForeground().setAlpha(0);
                popup.dismiss();
                myAdapter.notifyDataSetChanged();
                break;
            //case R.id.bpopupCancelAddNewTermCourse:
                layout_main.getForeground().setAlpha(0);
                popup.dismiss();
                break;*/
            

        }
    }

   /* public void showPop(Activity context){
        // Inflate the popup_layout.xml
        // RelativeLayout viewGroup = (RelativeLayout) context.findViewById(R.id.addWeights);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_add_new_termcourse, null);

        // Creating the PopupWindow
        popup = new PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popup.setFocusable(true);
        popup.setOutsideTouchable(false);

        etTermCourseId= (EditText) layout.findViewById(R.id.etNewTermCourseName);
        etTermCourseUnit = (EditText) layout.findViewById(R.id.etNewTermCourseUnit);

        //Dim the background
        layout_main.getForeground().setAlpha(220);


        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.CENTER, 0, 0);


        // Getting a reference to Close button, and close the popup when clicked.
        Button close = (Button) layout.findViewById(R.id.bpopupAddNewTermCourse);
        Button cancel = (Button) layout.findViewById(R.id.bpopupCancelAddNewTermCourse);
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
                break;
            case "B":
                bB.setBackgroundColor(getColor(R.color.colorPrimaryLight));
                bA.setBackgroundColor(Color.TRANSPARENT);
                bC.setBackgroundColor(Color.TRANSPARENT);
                bD.setBackgroundColor(Color.TRANSPARENT);
                bF.setBackgroundColor(Color.TRANSPARENT);
                break;
            case "C":
                bC.setBackgroundColor(getColor(R.color.colorPrimaryLight));
                bB.setBackgroundColor(Color.TRANSPARENT);
                bA.setBackgroundColor(Color.TRANSPARENT);
                bD.setBackgroundColor(Color.TRANSPARENT);
                bF.setBackgroundColor(Color.TRANSPARENT);
                break;
            case "D":
                bD.setBackgroundColor(getColor(R.color.colorPrimaryLight));
                bB.setBackgroundColor(Color.TRANSPARENT);
                bC.setBackgroundColor(Color.TRANSPARENT);
                bA.setBackgroundColor(Color.TRANSPARENT);
                bF.setBackgroundColor(Color.TRANSPARENT);
                break;
            case "F":
                bF.setBackgroundColor(getColor(R.color.colorPrimaryLight));
                bB.setBackgroundColor(Color.TRANSPARENT);
                bC.setBackgroundColor(Color.TRANSPARENT);
                bD.setBackgroundColor(Color.TRANSPARENT);
                bA.setBackgroundColor(Color.TRANSPARENT);
                break;
        }
    }*/
}
