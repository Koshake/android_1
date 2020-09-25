package com.koshake1.lesson1.temperature;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

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

import static com.koshake1.lesson1.data.Constants.KEY_CITY;
import static com.koshake1.lesson1.data.Constants.KEY_READ_RESULT;

public class WeatherRequestWorker extends Worker {

    private static final String TAG = "WEATHER";
    private static final int TIMEOUT = 5000;

    public WeatherRequestWorker(@NonNull Context context,
                                @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }


    @NonNull
    @Override
    public Result doWork() {
        Data output = null;
        Result workerResult;
        try {
            final String currentCity = getInputData().getString(KEY_CITY);
            final String url = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s",
                    currentCity,
                    BuildConfig.WEATHER_API_KEY);
            final URL uri = new URL(url);
            HttpsURLConnection urlConnection = null;
            try {
                urlConnection = (HttpsURLConnection) uri.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(TIMEOUT);
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String result = getLines(in);
                output = new Data.Builder()
                        .putString(KEY_READ_RESULT, result)
                        .build();
                workerResult = Result.success(output);
            } catch (FileNotFoundException e) {
                Log.e(TAG, String.format("Can't find information about %s", currentCity), e);
                workerResult = Result.failure();
            } catch (Exception e) {
                Log.e(TAG, "Fail connection", e);
                workerResult = Result.failure();
            } finally {
                if (null != urlConnection) {
                    urlConnection.disconnect();
                }
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, "Fail URI", e);
            workerResult = Result.failure();
            e.printStackTrace();
        }

        return workerResult;
    }

    private String getLines(BufferedReader in) {
        return in.lines().collect(Collectors.joining("\n"));
    }
}
