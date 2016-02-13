package login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import Constant.Constant;
import ui.Homepage;
import androidstudio.edbud.com.myapplication.R;

public class Login extends AppCompatActivity implements View.OnClickListener{

    Button bLogin;
    EditText etEmail, etPassword;
    TextView tvRegisterLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bLogin = (Button) findViewById(R.id.bLogin);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        tvRegisterLink = (TextView) findViewById(R.id.tvRegisterLink);

        bLogin.setOnClickListener(this);
        tvRegisterLink.setOnClickListener(this);
        Firebase.setAndroidContext(this);
    }

    public boolean isValidPassword(String password) {
        return password != null && password.trim().length() > 0;
    }
    public boolean isValidEmail(String email) {
        return email != null && email.trim().length() > 0 && email.indexOf("@") > 0;
    }
    public void authenticateUser(String email, String password){
        Firebase ref = new Firebase(Constant.DBURL);
        if(!isValidEmail(email)){
            etEmail.setError("Please enter a valid email address");
        }
        if(!isValidPassword(email)){
            etPassword.setError("Please enter a correct password");
        }
        ref.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                startActivity(new Intent(Login.this, Homepage.class));

            }
            @Override
            public void onAuthenticationError(FirebaseError error) {
                // Something went wrong :(

            }

        });
    }
    @Override
    public void onClick(View view) {

        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();



        switch (view.getId()) {

            case R.id.tvRegisterLink:
                startActivity(new Intent(this, Register.class));
                break;

            case R.id.bLogin:
                authenticateUser(email, password);
                final ProgressDialog progressDialog = new ProgressDialog(Login.this);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Authenticating...");
                progressDialog.show();
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                // On complete call either onLoginSuccess or onLoginFailed
                                // onLoginFailed();
                                progressDialog.dismiss();
                            }
                        }, 3000);

                break;



        }



    }
}
