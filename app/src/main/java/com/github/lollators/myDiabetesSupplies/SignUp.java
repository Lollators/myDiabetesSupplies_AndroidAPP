package com.github.lollators.myDiabetesSupplies;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {
    DatabaseHelper dbHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void back(View signupScreen){
        finish();
    }

    public void signUp(View signupScreen){
        EditText usernameField = findViewById(R.id.username_SignUp);
        EditText passwordField = findViewById(R.id.signup_password);
        EditText passwordRetypeField = findViewById(R.id.repeatPassword);


        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        String password_retype = passwordRetypeField.getText().toString();


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
        if(password_retype.trim().equalsIgnoreCase("")){
            passwordRetypeField.setError("This field cannot be blank");
        } else {
            passwordRetypeField.setError(null);
        }

        //if all field are completed
        if((usernameField.getError() == null || usernameField.getText().length() != 0) && (passwordField.getError() == null || passwordField.getText().length() != 0) && (passwordRetypeField.getError() == null || passwordRetypeField.getText().length() != 0)) {

            //if password matches its retype
            if (password.equals(password_retype)) {
                //check if user already exist - if not then create one
                if (dbHelper.createUser(username, password)) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                    alertDialogBuilder.setCancelable(true);
                    alertDialogBuilder.setMessage("User created successfully!");
                    alertDialogBuilder.setTitle("Success!");
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }

            } else {
                Toast error = Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG);
                error.show();
            }
        }


    }
}
