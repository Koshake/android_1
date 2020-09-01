package com.koshake1.lesson1;

import android.app.FragmentTransaction;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements  Constants {
    private final static int REQUEST_CODE = 1;
    private String currentCity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            currentCity = savedInstanceState.getString(CITY_RESULT, getResources().getString(R.string.saint_petersburg));
            TextView cityText = findViewById(R.id.textViewCity);
            cityText.setText(currentCity);
        } else {
            currentCity = getResources().getString(R.string.saint_petersburg);
        }
        Button buttonSelectCity = findViewById(R.id.buttonSelectCity);
        if (buttonSelectCity != null) {
            buttonSelectCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onButtonSelectCityClicked();
                }
            });
        }
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            buttonSelectCity.setVisibility(View.GONE);
        } else {
            buttonSelectCity.setVisibility(View.VISIBLE);
        }

        Button buttonYandex = findViewById(R.id.buttonYandex);
        buttonYandex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonYandexClicked();
            }
        });

        ImageButton buttonSettings = findViewById(R.id.imageButtonSettings);
        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonSettingsClicked();
            }
        });
        Log.d("MainActivity", "onCreate()");
        //Toast.makeText(getApplicationContext(), "onCreate()", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode != REQUEST_CODE) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
        if (requestCode == REQUEST_CODE) {
            TextView cityText = findViewById(R.id.textViewCity);
            if (data != null && !data.getStringExtra(CITY_RESULT).isEmpty()) {
                cityText.setText(data.getStringExtra(CITY_RESULT));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Toast.makeText(getApplicationContext(), "onDestroy()", Toast.LENGTH_SHORT).show();
        Log.d("MainActivity", "onDestroy()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Toast.makeText(getApplicationContext(), "onStart()", Toast.LENGTH_SHORT).show();
        Log.d("MainActivity", "onStart()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Toast.makeText(getApplicationContext(), "onStop()", Toast.LENGTH_SHORT).show();
        Log.d("MainActivity", "onStop()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Toast.makeText(getApplicationContext(), "onPause()", Toast.LENGTH_SHORT).show();
        Log.d("MainActivity", "onPause()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(getApplicationContext(), "onResume()", Toast.LENGTH_SHORT).show();
        Log.d("MainActivity", "onResume()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //Toast.makeText(getApplicationContext(), "onRestart()", Toast.LENGTH_SHORT).show();
        Log.d("MainActivity", "onRestart()");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //Toast.makeText(getApplicationContext(), "onSaveInstantState()", Toast.LENGTH_SHORT).show();
        Log.d("MainActivity", "onSaveInstanceState()");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        TextView cityText = findViewById(R.id.textViewCity);
        cityText.setText(savedInstanceState.getString(CITY_RESULT, getResources().getString(R.string.saint_petersburg)));

        //Toast.makeText(getApplicationContext(), "onSaveRestoreState()", Toast.LENGTH_SHORT).show();
        Log.d("MainActivity", "onRestoreInstanceState()");
    }

    private void onButtonSelectCityClicked() {
        //startActivity(new Intent(this, CityActivity.class));
        Intent intent = new Intent(MainActivity.this, CityActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    private void onButtonSettingsClicked() {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    private void onButtonYandexClicked() {
        Uri uri = Uri.parse(URL_YANDEX_WEATHER);
        Intent browser = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(browser);
    }


}