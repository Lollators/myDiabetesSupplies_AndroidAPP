package com.github.lollators.myDiabetesSupplies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ProductManagement extends AppCompatActivity {
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_management);
        Spinner spinner = (Spinner) findViewById(R.id.productTypes);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.productCategories ,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        dbHelper = DatabaseHelper.getInstance(getApplicationContext());
    }

    public void addProduct(View view){
        EditText serialNumberText = findViewById(R.id.productSerialNumberToAdd);
        Spinner productTypeText = findViewById(R.id.productTypes);
        EditText expirationDateText = findViewById(R.id.expirationDate);
        EditText binText = findViewById(R.id.binNumber);

        //get username and password
        String serialNumber = serialNumberText.getText().toString().trim();
        String productType = productTypeText.getSelectedItem().toString();
        String expirationDate = expirationDateText.getText().toString().trim();
        String bin = binText.getText().toString().trim();

        if(serialNumber.trim().equalsIgnoreCase("")){
            serialNumberText.setError("This field cannot be blank");
        } else {
            serialNumberText.setError(null);
        }
        if(expirationDate.trim().equalsIgnoreCase("")){
            expirationDateText.setError("This field cannot be blank");
        } else {
            expirationDateText.setError(null);
        }
        if(bin.trim().equalsIgnoreCase("")){
            binText.setError("This field cannot be blank");
        } else {
            binText.setError(null);
        }
        if((serialNumberText.getError() == null && serialNumberText.getText().length() != 0) &&
                (expirationDateText.getError() == null && expirationDateText.getText().length() != 0)
                && (binText.getError() == null && binText.getText().length() != 0)){
            if (dbHelper.addProduct(serialNumber,productType,expirationDate,bin)) {
                Toast success = Toast.makeText(this,
                        "Product Added to the database", Toast.LENGTH_LONG);
                success.show();
            } else {
                Toast error = Toast.makeText(this,
                        "Could not add product to database, check if already exists", Toast.LENGTH_LONG);
                error.show();
            }
        }
    }

    public void removeProduct(View view){

    }




}
