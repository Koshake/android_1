package com.koshake1.lesson1.temperature;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.gson.Gson;
import com.koshake1.lesson1.model.WeatherRequest;

import static com.koshake1.lesson1.data.Constants.KEY_READ_RESULT;
import static com.koshake1.lesson1.data.Constants.KEY_RESULT_CITY;
import static com.koshake1.lesson1.data.Constants.KEY_RESULT_DESCRIPTION;
import static com.koshake1.lesson1.data.Constants.KEY_RESULT_MAX_TEMP;
import static com.koshake1.lesson1.data.Constants.KEY_RESULT_MIN_TEMP;
import static com.koshake1.lesson1.data.Constants.KEY_RESULT_TEMP;
import static com.koshake1.lesson1.data.Constants.KEY_WEATHER_REQUEST;
import static com.koshake1.lesson1.data.Constants.KEY_WEATHER_RESULT;

public class WeatherParserWorker extends Worker {

    private final float TEMP_OFFSET = 273.15f;

    public WeatherParserWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        final String input = getInputData().getString(KEY_READ_RESULT);
        Gson gson = new Gson();
        final WeatherRequest weatherRequest = gson.fromJson(input, WeatherRequest.class);
        Data output = new Data.Builder()
                .putString(KEY_RESULT_CITY, weatherRequest.getName())
                .putInt(KEY_RESULT_TEMP, Math.round(weatherRequest.getMain().getTemp() - TEMP_OFFSET))
                .putInt(KEY_RESULT_MIN_TEMP, Math.round(weatherRequest.getMain().getTempMin() - TEMP_OFFSET))
                .putInt(KEY_RESULT_MAX_TEMP, Math.round(weatherRequest.getMain().getTempMax() - TEMP_OFFSET))
                .putString(KEY_RESULT_DESCRIPTION, weatherRequest.getWeather()[0].getDescription())
                .build();
        return Result.success(output);
    }
}
