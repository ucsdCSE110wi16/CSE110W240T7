package login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import Constant.Constant;
import ui.BaseActivity;
import ui.Homepage;
import androidstudio.edbud.com.myapplication.R;

public class Login extends AppCompatActivity implements View.OnClickListener{

    Button bLogin;
    EditText etEmail, etPassword;
    TextView tvRegisterLink;
    public SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sp = this.getSharedPreferences(Constant.myPrefer, Context.MODE_PRIVATE);
        //sp.edit().putBoolean("AutoLogin", false).apply();



        if(sp.getBoolean("AutoLogin", true) && sp.getString("check","").length() != 0 ){
            BaseActivity.uid = sp.getString("UID","DefaultStringIfNotFound");
            startActivity(new Intent(Login.this, Homepage.class));
        }


        setContentView(R.layout.activity_login);

        bLogin = (Button) findViewById(R.id.bLogin);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        tvRegisterLink = (TextView) findViewById(R.id.tvRegisterLink);

        bLogin.setOnClickListener(this);
        tvRegisterLink.setOnClickListener(this);
        Firebase.setAndroidContext(this);


    }




    public void authenticateUser(String email, String password){

        final Firebase ref = new Firebase(Constant.DBURL);
        sp.edit().putBoolean("AutoLogin", false).commit();
        sp.edit().putString("check","check").commit();

        ref.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(final AuthData authData) {
                //new to create a new user and fetch data from Firebase


                sp.edit().putBoolean("AutoLogin", true).commit();
                sp.edit().putString("UID",authData.getUid()).commit();


                BaseActivity.uid = authData.getUid();
                startActivity(new Intent(Login.this, Homepage.class));
            }

            @Override
            public void onAuthenticationError(FirebaseError error) {

                final ProgressDialog progressDialog = new ProgressDialog(Login.this);
                //progressDialog.setIndeterminate(true);

                switch (error.getCode()) {

                    case FirebaseError.USER_DOES_NOT_EXIST:
                        // handle a non existing user

                        progressDialog.setTitle("Sorry, the username does not exist");
                        progressDialog.show();
                        progressDialog.setCancelable(false);
                        bLogin.setVisibility(View.VISIBLE);

                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        // On complete call either onLoginSuccess or onLoginFailed
                                        // onLoginFailed();
                                        progressDialog.dismiss();
                                    }
                                }, 3700);

                        break;

                    case FirebaseError.INVALID_PASSWORD:
                        // handle an invalid password

                        progressDialog.setTitle("Sorry, the password is not correct");
                        progressDialog.show();
                        progressDialog.setCancelable(false);
                        bLogin.setVisibility(View.VISIBLE);

                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        // On complete call either onLoginSuccess or onLoginFailed
                                        // onLoginFailed();
                                        progressDialog.dismiss();
                                    }
                                }, 3700);
                        break;

                    default:
                        // handle other errors


                        progressDialog.setTitle("Sorry the input Email format is not correct");
                        progressDialog.show();
                        progressDialog.setCancelable(false);
                        bLogin.setVisibility(View.VISIBLE);

                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        // On complete call either onLoginSuccess or onLoginFailed
                                        // onLoginFailed();
                                        progressDialog.dismiss();
                                    }
                                }, 3700);
                        break;
                }
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

                //progressDialog.setIndeterminate(true);

                progressDialog.setTitle("Please be patient, our server is amazing!");
                progressDialog.setMessage("Authenticating...");
                progressDialog.show();
                progressDialog.setCancelable(false);

                bLogin.setVisibility(View.GONE);


                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                // On complete call either onLoginSuccess or onLoginFailed
                                // onLoginFailed();
                                progressDialog.dismiss();
                            }
                        }, 4000);
                break;
        }
    }
}
