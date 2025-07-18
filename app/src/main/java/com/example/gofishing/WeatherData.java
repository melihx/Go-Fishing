package com.example.gofishing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherData extends AppCompatActivity {
    protected TextView txtTemp;
    protected TextView txtWindSpeed;
    protected TextView txtHumidity;
    protected TextView txtMinTemp;
    protected TextView txtMaxTemp;
    protected TextView txtFeelsLike;
    protected Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_data);
        txtTemp = findViewById(R.id.txtTemp);
        txtWindSpeed = findViewById(R.id.txtWindSpeed);
        txtHumidity = findViewById(R.id.txtHumidity);
        txtMinTemp = findViewById(R.id.txtMinTemp);
        txtMaxTemp = findViewById(R.id.txtMaxTemp);
        txtFeelsLike = findViewById(R.id.txtFeelsLike);
        btnBack = findViewById(R.id.btnBack);

        APICallMethod(getIntent().getStringExtra("city"));

        btnBack.setOnClickListener(view -> {
            Intent i = new Intent(WeatherData.this, MainActivity.class);
            startActivity(i);
            finish();
        });
    }

    void APICallMethod(String city) {
        Thread t = new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient.Builder().build();

                Retrofit retrofit =
                        new Retrofit.Builder()
                                .baseUrl("https://api.api-ninjas.com/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .client(client)
                                .build();

                WeatherAPI api = retrofit.create(WeatherAPI.class);

                Call<WeatherAPI.info> info = api.api_get_info(city);

                Response<WeatherAPI.info> r = info.execute();
                if (r.isSuccessful()) {
                    WeatherAPI.info resp = r.body();
                    this.runOnUiThread(()-> {
                        txtTemp.setText(String.valueOf(resp.getTemp()));
                        txtWindSpeed.setText(String.valueOf(resp.getWindSpeed()));
                        txtHumidity.setText(String.valueOf(resp.getHumidity()));
                        txtMinTemp.setText(String.valueOf(resp.getMinTemp()));
                        txtMaxTemp.setText(String.valueOf(resp.getMaxTemp()));
                        txtFeelsLike.setText(String.valueOf(resp.getFeelsLike()));
                    });
                } else {
                    throw new RuntimeException("exception in server: "
                            + r.errorBody().string()
                    );
                }
            } catch (Exception e) {
                e.getLocalizedMessage();
            }
        });
        t.start();
    }
}