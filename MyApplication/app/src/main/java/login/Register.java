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
import model.user;


public class Register extends AppCompatActivity implements View.OnClickListener {

    EditText etName, etMajor, etEmail, etGraduate, etCollege, etPassword;
    Button bRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);
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

        /**
         * convert to string block
         */

        String fullName = etName.getText().toString();

        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String major = etMajor.getText().toString();
        String college = etCollege.getText().toString();
        String graduateDate = etGraduate.getText().toString();


        /**
         * Error handling
         */

        if(fullName.indexOf('.') >= 0){
            etName.setError("Sorry, the fullname cannot contains dots ");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Please input your username");
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

        /**
         * Set up the database
         */

        Firebase usersRef = ref.child("users").child(fullName);

        /**
         * Initialize user object
         */

        user myUser = new user(fullName, major, college, password, graduateDate, email);

        /**
         * Construct the user data structure
         */


        usersRef.setValue(myUser);

        switch (v.getId()) {
            case R.id.bRegister:
                startActivity(new Intent(this, Login.class));
                break;
        }
    }




}
