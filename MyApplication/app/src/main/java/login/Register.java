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

    EditText etName, etMajor, etEmail,etCollege, etPassword, etCurrentTerm, etGPA, etUnit;
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
        etCurrentTerm = (EditText) findViewById(R.id.etCurrentTerm);
        etGPA = (EditText) findViewById(R.id.etGPA);
        etUnit = (EditText) findViewById(R.id.etUnit);
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

    public static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
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
                String currentTerm = etCurrentTerm.getText().toString();
                double preGPA = Double.parseDouble(etGPA.getText().toString());
                int myUnit = Integer.parseInt(etUnit.getText().toString());
                String ID = result.get("uid").toString();
                Firebase start = new Firebase(Constant.DBURL);
                Firebase usersRef = start.child("userInfo").child(ID);

                BaseActivity.initialize = new User(fullName, major, college, password, email, ID, currentTerm, preGPA, myUnit);
                usersRef.setValue(BaseActivity.initialize);
                startActivity(new Intent(Register.this, Homepage.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

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
        String currentTerm = etCurrentTerm.getText().toString();
        String GPA = etGPA.getText().toString();
        String unit = etUnit.getText().toString();



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

        else if (TextUtils.isEmpty(GPA)){
            etGPA.setError("Please input you GPA");
        }

        else if (TextUtils.isEmpty(major)) {
            etMajor.setError("Please input your major");
            return;
        }

        else if (TextUtils.isEmpty(unit)) {
            etUnit.setError("Please input current unit");
            return;
        }

        else if(TextUtils.isEmpty(currentTerm)){
            etCurrentTerm.setError("Please input current term");
            return;
        }

        else if(!isDouble(GPA)){
            etGPA.setError("GPA need to be a number");
            return;
        }

        else if(!isInteger(unit)){
            etUnit.setError("Unit must be a number");
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
