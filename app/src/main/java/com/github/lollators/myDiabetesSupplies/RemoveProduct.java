package com.github.lollators.myDiabetesSupplies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RemoveProduct extends AppCompatActivity {
    DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_product);
        dbHelper = DatabaseHelper.getInstance(getApplicationContext());
    }

    public void removeProduct(View view){
        EditText serialNumberText = findViewById(R.id.productSerialNumberToRemove);
        String serialNumber = serialNumberText.getText().toString().trim();
        if(serialNumber.trim().equalsIgnoreCase("")){
            serialNumberText.setError("This field cannot be blank");
        } else {
            serialNumberText.setError(null);
        }

        if((serialNumberText.getError() == null && serialNumberText.getText().length() != 0)){
            if (dbHelper.removeProduct(serialNumber)) {
                Toast success = Toast.makeText(this,
                        "Product removed from the database", Toast.LENGTH_LONG);
                success.show();
            } else {
                Toast error = Toast.makeText(this,
                        "Could not locate product to remove", Toast.LENGTH_LONG);
                error.show();
            }
        }
    }
}
