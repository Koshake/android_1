package com.koshake1.lesson1.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public interface Constants {
    String CITY_RESULT = "City";
    String URL_YANDEX_WEATHER = "https://yandex.ru/pogoda/saint-petersburg";
    String KEY_CITY = "key_city";
    String KEY_READ_RESULT = "key_result";
    String KEY_WEATHER_RESULT = "key_weather_result";
    String KEY_WEATHER_REQUEST = "key_weather_request";
    String KEY_RESULT_CITY = "key_city_result";
    String KEY_RESULT_TEMP = "key_temp_result";
    String KEY_RESULT_MIN_TEMP= "key_min_temp_result";
    String KEY_RESULT_MAX_TEMP = "key_max_temp_result";
    String KEY_RESULT_DESCRIPTION = "key_description_result";
}
