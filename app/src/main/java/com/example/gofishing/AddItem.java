package com.example.gofishing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddItem extends AppCompatActivity {
    EditText editName, editDamName, editDamPhoneNumber, editFishVariety, editFishNumber;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        editName = findViewById(R.id.editFisherName);
        editDamName = findViewById(R.id.editDamName);
        editDamPhoneNumber = findViewById(R.id.editDamPhoneNumber);
        editFishVariety = findViewById(R.id.editFishVariety);
        editFishNumber = findViewById(R.id.editFishNumber);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(view -> {
            DatabaseHelper db = null;
            try {
                db = new DatabaseHelper(getApplicationContext());
                if (!Validation.Validate(editDamPhoneNumber.getText().toString(),
                        "(\\+\\d{1,3}( )?)?(\\d{2,4} ?)(\\d{2,4} ?)+\\d{2,4}")) {
                    throw new Exception("Invalid Phone Format");
                }

                db.insert(editName.getText().toString(),
                        editDamName.getText().toString(),
                        editDamPhoneNumber.getText().toString(),
                        editFishVariety.getText().toString(),
                        editFishNumber.getText().toString()
                );

                Toast.makeText(getApplicationContext(),
                                "Successful Insert", Toast.LENGTH_LONG)
                        .show();


            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),
                        e.getLocalizedMessage(),
                        Toast.LENGTH_LONG
                ).show();
            } finally {
                if (db != null) {
                    db.close();
                    db = null;
                }
            }

            Intent i = new Intent(AddItem.this, MainActivity.class);
            startActivity(i);
            finish();
        });
    }
}