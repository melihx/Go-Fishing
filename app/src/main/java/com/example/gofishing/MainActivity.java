package com.example.gofishing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ListAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public class CustomArrayAdapter extends ArrayAdapter<Stats> {
        private Context context;
        private int resource;

        public CustomArrayAdapter(@NonNull Context context, int resource, @NonNull List<Stats> objects) {
            super(context, resource, objects);
            this.context = context;
            this.resource = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Stats stats = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(resource,
                        parent, false);
            }
            TextView viewID = convertView.findViewById(R.id.listViewID);
            assert stats != null;
            viewID.setText(stats.getID());
            TextView viewName = convertView.findViewById(R.id.listViewName);
            viewName.setText(stats.getName());
            TextView viewDamName = convertView.findViewById(R.id.listViewDamName);
            viewDamName.setText(stats.getDamName());
            TextView viewDamPhoneNumber = convertView.findViewById(R.id.listViewDamPhoneNumber);
            viewDamPhoneNumber.setText(stats.getDamPhoneNumber());
            TextView viewFishVariety = convertView.findViewById(R.id.listViewFishVariety);
            viewFishVariety.setText(stats.getFishVariety());
            TextView viewFishNumber = convertView.findViewById(R.id.listViewFishNumber);
            viewFishNumber.setText(String.valueOf(stats.getFishNumber()));
            return convertView;
        }
    }

    protected Button btnAdd, btnCheck;
    protected EditText editCity;

    protected ListView listView;

    protected DatabaseHelper db;

    protected void FillListView() throws Exception {
        db = new DatabaseHelper(getApplicationContext());

        List<Stats> stats = db.select();
        CustomArrayAdapter customArrayAdapter =
                new CustomArrayAdapter(getApplicationContext(),
                        R.layout.activity_list_view,
                        stats);
        listView.clearChoices();
        listView.setAdapter(customArrayAdapter);
        db.close();
        db = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            FillListView();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    e.getLocalizedMessage(),
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        btnAdd = findViewById(R.id.btnAdd);
        editCity = findViewById(R.id.editCity);
        btnCheck = findViewById(R.id.btnCheck);

        try {
            FillListView();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String ID = ((TextView) view.findViewById(R.id.listViewID)).getText().toString();
                String Name = ((TextView) view.findViewById(R.id.listViewName)).getText().toString();
                String DamName = ((TextView) view.findViewById(R.id.listViewDamName)).getText().toString();
                String DamPhoneNumber = ((TextView) view.findViewById(R.id.listViewDamPhoneNumber)).getText().toString();
                String FishVariety = ((TextView) view.findViewById(R.id.listViewFishVariety)).getText().toString();
                String FishNumber = ((TextView) view.findViewById(R.id.listViewFishNumber)).getText().toString();

                Intent intent = new Intent(MainActivity.this, UpdateOrDelete.class);
                Bundle b = new Bundle();
                b.putString("ID", ID);
                b.putString("Name", Name);
                b.putString("DamName", DamName);
                b.putString("DamPhoneNumber", DamPhoneNumber);
                b.putString("FishVariety", FishVariety);
                b.putString("FishNumber", FishNumber);
                intent.putExtras(b);
                startActivityForResult(intent, 200, b);

            }
        });

        btnAdd.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, AddItem.class);
            startActivity(i);
        });

        btnCheck.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, WeatherData.class);
            Bundle bundle = new Bundle();
            i.putExtra("city",editCity.getText().toString());
            startActivity(i);
        });
    }
}