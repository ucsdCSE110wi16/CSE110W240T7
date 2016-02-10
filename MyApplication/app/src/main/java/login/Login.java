package login;

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
    boolean isLoginSuccess = false;


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

    @Override
    public void onClick(View view) {

        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();



        Firebase ref = new Firebase(Constant.DBURL);
        ref.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                isLoginSuccess = true;
                Log.v("break2", "break2");
                Log.v("0", "0");
            }

            @Override
            public void onAuthenticationError(FirebaseError error) {
                // Something went wrong :(
                Log.v("break3", "break3");
                isLoginSuccess = false;
                switch (error.getCode()) {
                    case FirebaseError.USER_DOES_NOT_EXIST:
                        //etPassword.setError("1");
                        Log.v("1", "2");
                        // handle a non existing user
                        break;
                    case FirebaseError.INVALID_PASSWORD:
                        //etPassword.setError("2");
                        Log.v("2", "2");
                        // handle an invalid password
                        break;
                    default:
                        //etPassword.setError("3");
                        Log.v("3", "2");
                        // handle other errors
                        break;
                }
            }

        });
        switch (view.getId()) {

            case R.id.tvRegisterLink:
                startActivity(new Intent(this, Register.class));
                break;

            case R.id.bLogin:
                if (TextUtils.isEmpty(email)) {
                    Log.v("break1","break1");
                    etEmail.setError("Please input your email");
                    return;
                }
                else if (TextUtils.isEmpty(password)) {
                    Log.v("break2","break2");
                    etPassword.setError("Please input a password");
                    return;
                }
                Log.v("break4","break4");
                if(isLoginSuccess != true) {
                    Log.v("break5","break5");
                    Log.v("1", "1");
                    return;

                }
                startActivity(new Intent(this, Homepage.class));
                break;

        }



    }
}
