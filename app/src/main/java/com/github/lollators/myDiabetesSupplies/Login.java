package com.github.lollators.myDiabetesSupplies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = DatabaseHelper.getInstance(getApplicationContext());
    }

    public void login(View loginScreen) {
        EditText usernameField = findViewById(R.id.username);
        EditText passwordField = findViewById(R.id.password);

        //get username and password
        String username = usernameField.getText().toString().trim();
        String password = usernameField.getText().toString().trim();

        if(username.trim().equalsIgnoreCase("")){
            usernameField.setError("This field cannot be blank");
        } else {
            usernameField.setError(null);
        }
        if(password.trim().equalsIgnoreCase("")){
            passwordField.setError("This field cannot be blank");
        } else {
            passwordField.setError(null);
        }
        if((usernameField.getError() == null && usernameField.getText().length() != 0) &&
                (passwordField.getError() == null && passwordField.getText().length() != 0)){
            if (dbHelper.login(username, password)) {
                //TODO: go to next screen instead of displaying toast
                Toast success = Toast.makeText(this,
                        "User Authenticated!", Toast.LENGTH_LONG);
                success.show();
            } else {
                Toast error = Toast.makeText(this,
                        "Wrong username or password", Toast.LENGTH_LONG);
                error.show();
            }
        }
    }

    public void signup(View loginScreen){
        Intent myIntent = new Intent(this, SignUp.class);
        startActivity(myIntent);
    }


}
