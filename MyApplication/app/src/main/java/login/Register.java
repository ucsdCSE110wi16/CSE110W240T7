package login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import com.firebase.client.Firebase;

import java.util.ArrayList;

import Constant.Constant;
import androidstudio.edbud.com.myapplication.R;
import model.Courses;
import model.User;


public class Register extends AppCompatActivity implements View.OnClickListener {

    EditText etName, etMajor, etUsername, etGraduate, etCollege, etPassword;
    Button bRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = (EditText) findViewById(R.id.etName);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etMajor = (EditText) findViewById(R.id.etMajor);
        etCollege = (EditText) findViewById(R.id.etCollege);
        etGraduate = (EditText) findViewById(R.id.etGraduate);

        bRegister = (Button) findViewById(R.id.bRegister);
        bRegister.setOnClickListener(this);
        Firebase.setAndroidContext(this);

    }

    @Override
    public void onClick(View v) {

        /**
         *Initialize the data base
         */
        Firebase ref = new Firebase(Constant.DBURL);

        /*
        ArrayList<Integer> courseList = new ArrayList<Integer>();
        courseList.add(1);
        courseList.add(3);
        courseList.add(2);
        courseList.add(4);
        */

        /**
         * convert to string block
         */

        String fullName = etName.getText().toString();
        String username= etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String major = etMajor.getText().toString();
        String college = etCollege.getText().toString();
        String graduateDate = etGraduate.getText().toString();




        if (TextUtils.isEmpty(username)) {
            etUsername.setError("Please input your username");
            return;
        }
        else if (TextUtils.isEmpty(password)) {
            etPassword.setError("Please input a password");
            return;
        }
        else if (TextUtils.isEmpty(fullName)) {
            etName.setError("Please input your name");
            return;
        }
        else if (TextUtils.isEmpty(college)) {
            etCollege.setError("Please input your college");
            return;
        }
        else if (TextUtils.isEmpty(major)) {
            etMajor.setError("Please input your major");
            return;
        }
        else if (TextUtils.isEmpty(graduateDate)) {
            etGraduate.setError("Please input graduate date");
            return;
        }
        //int graduateDate = Integer.parseInt(etCollege.getText().toString());

        /**
         * Set up the database
         */

        Firebase usersRef = ref.child("users").child(username);


        /**
         * Initialize user object
         */

        User myUser = new User(fullName, major, college, password, graduateDate);
        //ArrayList<Courses> courseList = myUser.getUsrArrayList();
        Courses test_1 = new Courses();
        Courses test_2 = new Courses();
        Courses test_3 = new Courses();

        ArrayList<Courses> courseList = new ArrayList<>();
        courseList.add(test_1);
        courseList.add(test_2);
        courseList.add(test_3);

        /**
         * Construct the user data structure
         */

        usersRef.setValue(myUser);

        usersRef.child("fullName").setValue(fullName);
        usersRef.child("major").setValue(major);
        usersRef.child("college").setValue(college);
        usersRef.child("password").setValue(password);
        usersRef.child("graduateDate").setValue(graduateDate);
        //usersRef.child("courseList").setValue(courseList);

        //usersRef.setValue(courseList);


        switch (v.getId()) {
            case R.id.bRegister:
                startActivity(new Intent(this, Login.class));
                break;
        }
    }




}
