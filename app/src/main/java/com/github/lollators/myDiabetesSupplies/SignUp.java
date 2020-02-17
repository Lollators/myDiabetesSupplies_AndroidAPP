package com.github.lollators.myDiabetesSupplies;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
        String username = ((EditText) findViewById(R.id.username)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();
        String password_retype = ((EditText) findViewById(R.id.repeatPassword)).getText().toString();

        if(password.equals(password_retype)){
        }


    }
}
