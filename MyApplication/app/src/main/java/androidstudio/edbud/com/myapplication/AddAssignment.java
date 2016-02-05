package androidstudio.edbud.com.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.DatePicker;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddAssignment extends AppCompatActivity implements View.OnClickListener {

    private Button bAddAssignment;
    private EditText etScore, etAssignmentID, etDueDate;
    private DatePickerDialog dueDatePickerDialog;

    private SimpleDateFormat dateFormatter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);


        dateFormatter = new SimpleDateFormat("dd-mm-yyyy", Locale.US);
        
        etDueDate = (EditText) findViewById(R.id.etDueDate);
        etDueDate.setInputType(InputType.TYPE_NULL);
        setDateTimeField();

        bAddAssignment = (Button) findViewById(R.id.bAddAssignment);
        bAddAssignment.setOnClickListener(this);

        etAssignmentID = (EditText) findViewById(R.id.etAssignmentID);
        //etScore = (EditText) findViewById(R.id.etScore);
        //etDate = (EditText) findViewById(R.id.calendarView);
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.bAddAssignment:
                String hw = etAssignmentID.getText().toString();
//                int percent = Integer.parseInt(etScore.getText().toString());
                //int data = Integer.parseInt(etDate.getText().toString());
                CoursePage.myCourse.get(CoursePage.p).addAssignment(hw,hw);
                startActivity(new Intent(this, CoursePage.class));
                startActivity(new Intent(this, IndividualCourse.class));
                break;
            case R.id.etDueDate:
                dueDatePickerDialog.show();

    }
    }


    private void setDateTimeField(){
        etDueDate.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        dueDatePickerDialog =  new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
 
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etDueDate.setText(dateFormatter.format(newDate.getTime()));
            }
 
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }
}
