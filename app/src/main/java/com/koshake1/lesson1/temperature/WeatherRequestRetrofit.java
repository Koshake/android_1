package com.koshake1.lesson1.temperature;

import com.koshake1.lesson1.BuildConfig;
import com.koshake1.lesson1.model.OpenWeather;
import com.koshake1.lesson1.model.WeatherRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherRequestRetrofit {
    private OpenWeather openWeather;
    private  TemperatureFragment fragment;

    public WeatherRequestRetrofit(TemperatureFragment fragment) {
        this.fragment = fragment;
    }

   void initRetorfit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        openWeather = retrofit.create(OpenWeather.class);
    }
    void requestRetrofit(String city, String keyApi) {
        openWeather.loadWeather(city, keyApi)
                .enqueue(new Callback<WeatherRequest>() {
                    @Override
                    public void onResponse(Call<WeatherRequest> call, Response<WeatherRequest> response) {
                        if (response.body() != null) {
                            fragment.displayWeather(response);
                            fragment.changeImageOnTemperature(response.body().getMain().getTemp());
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherRequest> call, Throwable t) {
                        fragment.setMessage("Connection fail!");
                    }
                });
    }

    void requestRetrofit(String lat, String lon, String keyApi) {
        openWeather.loadWeatherByCoord(lat, lon, keyApi)
                .enqueue(new Callback<WeatherRequest>() {
                    @Override
                    public void onResponse(Call<WeatherRequest> call, Response<WeatherRequest> response) {
                        if (response.body() != null) {
                            fragment.displayWeather(response);
                            fragment.changeImageOnTemperature(response.body().getMain().getTemp());
                            fragment.showBadWeatherNotifications(response);
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherRequest> call, Throwable t) {
                        fragment.setMessage("Connection fail!");
                    }
                });
    }
}