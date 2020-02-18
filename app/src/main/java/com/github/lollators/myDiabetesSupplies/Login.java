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
        //get username and password
        String username = ((EditText) findViewById(R.id.username)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();
        if(dbHelper.login(username, password)){
            System.out.println("AUTH!!");
            Toast success =  Toast.makeText(this, "AUTHENTICATED!", Toast.LENGTH_LONG);
            success.show();
        } else {
            Toast error =  Toast.makeText(this, "Wrong username or password", Toast.LENGTH_LONG);
            error.show();
        }
    }

    public void signup(View loginScreen){
        Intent myIntent = new Intent(this, SignUp.class);
        startActivity(myIntent);
    }


}
