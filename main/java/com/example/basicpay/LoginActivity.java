package com.example.basicpay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.basicpay.data.LoggedInUser;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginActivity extends AppCompatActivity {
    LoggedInUser loggers = new LoggedInUser();
    private static Logger l = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText usernameEditText = findViewById(R.id.txtUserName);
        final EditText passwordEditText = findViewById(R.id.txtPassword);
        final Button loginButton = findViewById(R.id.btnLogin);

        loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkLogin(usernameEditText, passwordEditText);
                }
            }
        );
    }


    private void checkLogin(EditText usernameEditText, EditText passwordEditText) {
        if (usernameEditText.getText().toString().equals("")) {
            showLoginFailed("Username is empty");
        }
        else if (passwordEditText.getText().toString().equals("")) {
            showLoginFailed("Password is empty");
        }
        else if (loggers.getPassWord().equals(passwordEditText.getText().toString()) &&
                 loggers.getUserId().equals(usernameEditText.getText().toString())) {
            updateUiWithUser();
        } else {
            showLoginFailed("Wrong Username or Password");
        }

    }

    private void updateUiWithUser() {
        String welcome = getString(R.string.welcome) + loggers.getDisplayName();
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
        loggers.setLogState(1);
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", loggers);
        l.log(Level.INFO, loggers.getLogState() + "true");
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private void showLoginFailed(String errorTxt) {
        Toast.makeText(getApplicationContext(), errorTxt, Toast.LENGTH_SHORT).show();
        loggers.setLogState(0);
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", loggers);
        l.log(Level.INFO, loggers.getLogState() + "false");
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
