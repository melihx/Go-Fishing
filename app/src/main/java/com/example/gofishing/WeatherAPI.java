package com.example.gofishing;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherAPI {

    public class info {
        public double wind_speed;
        public int temp;
        public int humidity;
        public int min_temp;
        public int feels_like;
        public int max_temp;

        public info(info i){
            this.wind_speed = i.wind_speed;
            this.temp = i.temp;
            this.humidity= i.humidity;
            this.min_temp=i.min_temp;
            this.max_temp=i.max_temp;
            this.feels_like=i.feels_like;
        }

        public double getWindSpeed() {
            return wind_speed;
        }

        public int getTemp() {
            return temp;
        }

        public int getHumidity() {
            return humidity;
        }

        public int getMinTemp() {
            return min_temp;
        }

        public int getMaxTemp() {
            return max_temp;
        }

        public int getFeelsLike() {
            return feels_like;
        }

    }

    @Headers("X-Api-Key:" + "qm0gIiZgzbujp7/RKekqjw==NnUpbSNKz0VxjHaz")
    @GET("/v1/weather?")
    public Call<info> api_get_info(@Query("city") String city);
}
