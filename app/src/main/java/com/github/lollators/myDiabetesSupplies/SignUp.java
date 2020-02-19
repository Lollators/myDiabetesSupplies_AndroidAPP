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
        String username = ((EditText) findViewById(R.id.username_SignUp)).getText().toString();
        String password = ((EditText) findViewById(R.id.signup_password)).getText().toString();
        String password_retype = ((EditText) findViewById(R.id.repeatPassword)).getText().toString();

        //if password matches its retype
        if(password.equals(password_retype)){
            //check if user already exist - if not then create one
            if(dbHelper.createUser(username,password)){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setCancelable(true);
                alertDialogBuilder.setMessage("User created successfully!");
                alertDialogBuilder.setTitle("Success!");
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }

        } else {
            Toast error =  Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG);
            error.show();
        }


    }
}
