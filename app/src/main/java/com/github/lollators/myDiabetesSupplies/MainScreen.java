package com.github.lollators.myDiabetesSupplies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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

    public void add(android.view.View view){
        TableLayout myTable = findViewById(R.id.table);
        TableRow newRow = new TableRow(this);
        newRow.setBackground(ContextCompat.getDrawable(this, R.drawable.tableborder));
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);


        newRow.setLayoutParams(lp);
        TextView name = new TextView(this);
        TextView category = new TextView(this);
        TextView expirationDate = new TextView(this);
        TextView box = new TextView(this);
        name.setText("test");
        category.setText("Sites");
        expirationDate.setText("02/20/2021");
        box.setText("A");

        newRow.addView(name);
        newRow.addView(category);
        newRow.addView(expirationDate);
        newRow.addView(box);
        myTable.addView(newRow);
    }




}
