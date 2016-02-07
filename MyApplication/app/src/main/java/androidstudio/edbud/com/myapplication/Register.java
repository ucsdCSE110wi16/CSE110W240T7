package androidstudio.edbud.com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import com.firebase.client.Firebase;

import Constant.Constant;


public class Register extends AppCompatActivity implements View.OnClickListener {

    EditText etName, etMajor, etUsername, etGraduate, etCollege, etPassword;
    Button bRegister;

    @Override

    public void onCreate(){
        super.onCreate();
    }


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



        Firebase ref = new Firebase(Constant.DBURL);
        String username = etName.getText().toString();
        ref.child("username").setValue(username);

    }

    @Override
    public void onClick(View v) {



        switch (v.getId()) {
            case R.id.bRegister:
                startActivity(new Intent(this, Login.class));
                break;
        }
    }




}
