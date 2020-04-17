package com.github.lollators.myDiabetesSupplies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class MainScreen extends AppCompatActivity {
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        Spinner spinner = findViewById(R.id.productType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.productCategories ,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        dbHelper = DatabaseHelper.getInstance(getApplicationContext());
    }

    public void logout(android.view.View view){
        Intent myIntent = new Intent(this, Login.class);
        startActivity(myIntent);
        finish();
    }

    private void cleanTable(TableLayout table) {

        int childCount = table.getChildCount();

        // Remove all rows except the first one
        if (childCount > 1) {
            table.removeViews(1, childCount - 1);
        }
    }

    public void refresh(android.view.View view){
        TableLayout myTable = findViewById(R.id.table);
        cleanTable(myTable);
        Spinner productType = findViewById(R.id.productType);

        //get products from DB based on selection
        ArrayList<Product> myProducts = dbHelper.getProduct(productType.getSelectedItem().toString());

        for(Product x : myProducts){
            TableRow newRow = new TableRow(this);
            newRow.setBackground(ContextCompat.getDrawable(this, R.drawable.tableborder));
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            newRow.setLayoutParams(lp);

            TextView serialNumber = new TextView(this);
            TextView category = new TextView(this);
            TextView expirationDate = new TextView(this);
            TextView box = new TextView(this);

            serialNumber.setText(x.getSerialNumber());
            category.setText(x.getName());
            expirationDate.setText(x.getExpirationDate());
            box.setText(x.getBin());

            newRow.addView(serialNumber);
            newRow.addView(category);
            newRow.addView(expirationDate);
            newRow.addView(box);

            myTable.addView(newRow);
        }

    }

    public void add(android.view.View view){
        Intent myIntent = new Intent(this, AddProduct.class);
        startActivity(myIntent);
    }

    public void remove(android.view.View view){
        Intent myIntent = new Intent(this, RemoveProduct.class);
        startActivity(myIntent);
    }




}
