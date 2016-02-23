package login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.Firebase.AuthResultHandler;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Constant.Constant;
import androidstudio.edbud.com.myapplication.R;
import model.Courses;
import model.User;
import ui.BaseActivity;
import ui.Homepage;


public class Register extends AppCompatActivity implements View.OnClickListener {

    EditText etName, etMajor, etEmail, etGraduate, etCollege, etPassword, etCurrentTerm;
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
        etCurrentTerm = (EditText) findViewById(R.id.etCurrentTerm);
        bRegister = (Button) findViewById(R.id.bRegister);
        bRegister.setOnClickListener(this);
        Firebase.setAndroidContext(this);

    }


    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    public void RegisterUser(){
        Firebase ref = new Firebase(Constant.DBURL);
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        ref.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {

            @Override
            public void onSuccess(Map<String, Object> result) {
                Log.v("Entered","Success");
                String fullName = etName.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String major = etMajor.getText().toString();
                String college = etCollege.getText().toString();
                String graduateDate = etGraduate.getText().toString();
                String currentTerm = etCurrentTerm.getText().toString();
                String ID = result.get("uid").toString();
                Firebase start = new Firebase(Constant.DBURL);
                Firebase usersRef = start.child("userInfo").child(ID);

                BaseActivity.initialize = new User(fullName, major, college, password, graduateDate, email,ID, currentTerm);
                usersRef.setValue(BaseActivity.initialize);
                startActivity(new Intent(Register.this, Homepage.class));

            }
            @Override
            public void onError(FirebaseError firebaseError) {
                // there was an error
            }
        });

    }

    @Override
    public void onClick(View v) {

        /**
         *Initialize the data base
         */
        //Firebase ref = new Firebase(Constant.DBURL);

        /**
         * convert to string block
         */

        String fullName = etName.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String major = etMajor.getText().toString();
        String college = etCollege.getText().toString();
        String graduateDate = etGraduate.getText().toString();
        String currentTerm = etCurrentTerm.getText().toString();



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

        else if(!email.contains("@")){
            etEmail.setError("The input email format is not correct, must follow xxx@.com-format");
            return;
        }

        else if (TextUtils.isEmpty(password)) {
            etPassword.setError("Please input a password");
            return;
        }

        else if(password.length() < 6){
            etPassword.setError("Password must have at least six characters");
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

        else if(TextUtils.isEmpty(currentTerm)){
            etCurrentTerm.setError("Please input current term");
        }

        else if(!isInteger(graduateDate)){
            etGraduate.setError("Graduate date must be a number");
            return;
        }


        /**
         * Construct the user data structure
         */



        switch (v.getId()) {
            case R.id.bRegister:
                RegisterUser();
                break;
        }
    }




}
