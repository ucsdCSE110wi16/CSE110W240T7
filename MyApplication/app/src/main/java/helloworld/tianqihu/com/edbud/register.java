package helloworld.tianqihu.com.edbud;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

public class register extends AppCompatActivity implements View.OnClickListener{

    Button bRegister;
    EditText etName, etUsername, etCollege, etPassword, etMajor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etName = (EditText) findViewById(R.id.etName);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etCollege = (EditText) findViewById(R.id.etCollege);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etMajor = (EditText) findViewById(R.id.etMajor);
        bRegister = (Button) findViewById(R.id.bRegister);

        bRegister.setOnClickListener(this);
    }
        @Override
        public void onClick(View v){

            switch(v.getId()){
                case R.id.bRegister:
                    break;
            }

        }



}

