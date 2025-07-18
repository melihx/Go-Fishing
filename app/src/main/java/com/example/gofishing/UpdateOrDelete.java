package com.example.gofishing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateOrDelete extends AppCompatActivity {

    protected String ID;
    protected EditText editName, editDamName, editDamPhoneNumber, editFishVariety, editFishNumber;
    protected Button btnUpdate, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_or_delete);

        editName = findViewById(R.id.editFisherName);
        editDamName = findViewById(R.id.editDamName);
        editDamPhoneNumber = findViewById(R.id.editDamPhoneNumber);
        editFishVariety = findViewById(R.id.editFishVariety);
        editFishNumber = findViewById(R.id.editFishNumber);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        Bundle b = getIntent().getExtras();

        Stats s = new Stats(
                b.getString("ID"),
                b.getString("Name"),
                b.getString("DamName"),
                b.getString("DamPhoneNumber"),
                b.getString("FishVariety"),
                b.getString("FishNumber")
        );

        if (s == null) {
            Toast.makeText(getApplicationContext(),
                    "Data not found",
                    Toast.LENGTH_LONG
            ).show();
        }
        ID = s.getID();
        editName.setText(s.getName());
        editDamName.setText(s.getDamName());
        editDamPhoneNumber.setText(s.getDamPhoneNumber());
        editFishVariety.setText(s.getFishVariety());
        editFishNumber.setText(s.getFishNumber());

        btnUpdate.setOnClickListener(view -> {
            DatabaseHelper db = null;
            try {
                db = new DatabaseHelper(getApplicationContext());
                if (!Validation.Validate(editDamPhoneNumber.getText().toString(),
                        "(\\+\\d{1,3}( )?)?(\\d{2,4} ?)(\\d{2,4} ?)+\\d{2,4}")) {
                    throw new Exception("Invalid Phone Format");
                }

                db.update(
                        new Stats(
                                ID,
                                editName.getText().toString(),
                                editDamName.getText().toString(),
                                editDamPhoneNumber.getText().toString(),
                                editFishVariety.getText().toString(),
                                editFishNumber.getText().toString()
                        )
                );

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

            finishActivity(301);
            Intent i = new Intent(UpdateOrDelete.this, MainActivity.class);
            startActivity(i);
            finish();
        });

        btnDelete.setOnClickListener(view -> {
            DatabaseHelper db = null;
            try {
                db = new DatabaseHelper(getApplicationContext());
                if (!Validation.Validate(editDamPhoneNumber.getText().toString(),
                        "(\\+\\d{1,3}( )?)?(\\d{2,4} ?)(\\d{2,4} ?)+\\d{2,4}")) {
                    throw new Exception("Invalid Phone Format");
                }

                db.delete(
                        new Stats(
                                ID,
                                editName.getText().toString(),
                                editDamName.getText().toString(),
                                editDamPhoneNumber.getText().toString(),
                                editFishVariety.getText().toString(),
                                editFishNumber.getText().toString()
                        )
                );

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

            finishActivity(301);
            Intent i = new Intent(UpdateOrDelete.this, MainActivity.class);
            startActivity(i);
            finish();
        });
    }
}