package com.koshake1.lesson1.temperature;

import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.koshake1.lesson1.BuildConfig;
import com.koshake1.lesson1.model.WeatherRequest;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

public class WeatherHandler {
    private static final String TAG = "WEATHER";
    private TemperatureFragment fragment;

    public WeatherHandler(TemperatureFragment fragment) {
        this.fragment = fragment;
    }


    public void updateWeather(){
        try {
            final String currentCity = fragment.getCurrentCity();
            final String url = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s",
                    currentCity,
                    BuildConfig.WEATHER_API_KEY);
            final URL uri = new URL(url);
            final Handler handler = new Handler(); // Запоминаем основной поток
            new Thread(new Runnable() {
                public void run() {
                    HttpsURLConnection urlConnection = null;
                    try {
                        urlConnection = (HttpsURLConnection) uri.openConnection();
                        urlConnection.setRequestMethod("GET");
                        urlConnection.setReadTimeout(10000);
                        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        String result = getLines(in);
                        Gson gson = new Gson();
                        final WeatherRequest weatherRequest = gson.fromJson(result, WeatherRequest.class);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                fragment.displayWeather(weatherRequest);
                            }
                        });
                    } catch (FileNotFoundException e) {
                        fragment.setMessage(String.format("Can't find information about %s", currentCity));
                    } catch (Exception e) {
                        Log.e(TAG, "Fail connection", e);
                        fragment.setMessage("Connection failed!");
                    } finally {
                        if (null != urlConnection) {
                            urlConnection.disconnect();
                        }
                    }
                }
            }).start();
        } catch (MalformedURLException e) {
            Log.e(TAG, "Fail URI", e);
            fragment.setMessage("Fail URI");
            e.printStackTrace();
        }
    }

    private String getLines(BufferedReader in) {
        return in.lines().collect(Collectors.joining("\n"));
    }
}
