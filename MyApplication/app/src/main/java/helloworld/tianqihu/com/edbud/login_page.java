package helloworld.tianqihu.com.edbud;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;

public class login_page extends AppCompatActivity implements View.OnClickListener{

    Button bLogin;
    EditText etUsername, etPassword;
    TextView tvRegisterLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bLogin = (Button) findViewById(R.id.bLogin);
        tvRegisterLink = (TextView) findViewById(R.id.tvRegisterLink);
        bLogin.setOnClickListener(this);
        tvRegisterLink.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){

        switch(v.getId()){
            case R.id.bLogin:
                break;
            case R.id.tvRegisterLink:
                startActivity(new Intent(this,register.class));
                break;
        }





    }
}
