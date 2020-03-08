package com.github.lollators.myDiabetesSupplies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
    }

    public void logout(android.view.View view){
        Intent myIntent = new Intent(this, Login.class);
        startActivity(myIntent);
        finish();
    }

    public void refresh(android.view.View view){

    }




}
