package ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import androidstudio.edbud.com.myapplication.R;
import model.Category;
import model.Courses;
import model.IndividualCourse;

public class AddAssignment extends AppCompatActivity implements View.OnClickListener {

    private Button bAddAssignment;
    private EditText etScore, etAssignmentID, etDueDate, etChooseWeight;
    private DatePickerDialog dueDatePickerDialog;
    private ArrayList weights;
    private HashMap<String, Category> categories;
    private RadioGroup weightsGroup;
    private RadioButton weightButton;
    private int y,m,d;
    private Courses mycourse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);
        mycourse = BaseActivity.initialize.getTerm(BaseActivity.initialize.getCurrTerm()).getTermCourses().get(CoursePage.p);
        //weights = mycourse.getWeights();
        categories = mycourse.getCategories();

    
        
        etDueDate = (EditText) findViewById(R.id.etDueDate);
        etDueDate.setInputType(InputType.TYPE_NULL);
        setDateTimeField();

        bAddAssignment = (Button) findViewById(R.id.bAddAssignment);
        bAddAssignment.setOnClickListener(this);

        etAssignmentID = (EditText) findViewById(R.id.etAssignmentID);

        weightsGroup = (RadioGroup) findViewById(R.id.radioWeights);
        RadioButton rdbtn;

        int i = 0;
        for (Category temp:categories.values()) {
            rdbtn = new RadioButton(this);
            rdbtn.setId(i);
            rdbtn.setText(temp.getCategoryName());
            weightsGroup.addView(rdbtn);
            ++i;
        }

    }



    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.bAddAssignment:
                String hw = etAssignmentID.getText().toString();
                String date = etDueDate.getText().toString();
                int selectedId = weightsGroup.getCheckedRadioButtonId();
                if(TextUtils.isEmpty(hw)) {
                    etAssignmentID.setError("Please input assignment name");
                    return;
                }
                else if(TextUtils.isEmpty(date)) {
                    etDueDate.setError("Please input assignment due date");
                    return;
                }
                else if(selectedId == -1){
                    Toast.makeText(this, "Please choose assignment type",Toast.LENGTH_LONG).show();
                    return;

                }else if(y == 0 && m == 0 && d ==0){
                    Toast.makeText(this, "Please pick a due date", Toast.LENGTH_LONG).show();
                }

                // find the radiobutton by returned id
                weightButton = (RadioButton) findViewById(selectedId);


                if(!mycourse.addAssignment(weightButton.getText().toString(), hw, y, m, d)){
                Toast.makeText(this, "This assignment has already been added", Toast.LENGTH_LONG).show();
                }
                startActivity(new Intent(this, IndividualCourse.class));
                break;
            case R.id.etDueDate:
                dueDatePickerDialog.show();
                break;

        }
    }


    private void setDateTimeField(){
        etDueDate.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        dueDatePickerDialog =  new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
 
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                y = year;
                m = monthOfYear;
                d = dayOfMonth;
                etDueDate.setText(new StringBuilder().append(dayOfMonth)
                        .append("-").append(monthOfYear + 1).append("-").append(year)
                        .append(" "));
            }
 
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }
}
