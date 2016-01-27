package androidstudio.edbud.com.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class AddCourse extends AppCompatActivity implements View.OnClickListener{

    Button bAddCourse;
    EditText etcourseID, etcourseUnit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        bAddCourse = (Button) findViewById(R.id.bAddCourse);
        bAddCourse.setOnClickListener(this);

        etcourseID = (EditText) findViewById(R.id.etCourseID);
        etcourseUnit = (EditText) findViewById(R.id.etUnit);



    }


    @Override
    public void onClick(View view){
        String courseID = etcourseID.getText().toString();
        Homepage.courses.add(courseID);
        int courseUnit = Integer.parseInt(etcourseUnit.getText().toString());

        startActivity(new Intent(this, Homepage.class));


    }



}
