package com.koshake1.lesson1.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeather {
    @GET("data/2.5/weather")
    Call<WeatherRequest> loadWeather(@Query("q") String cityCountry, @Query("appid") String keyApi);

    @GET("data/2.5/weather")
    Call<WeatherRequest> loadWeatherByCoord(@Query("lat") String latitude,
            @Query("lon") String longitude,
            @Query("appid") String keyApi);
}
