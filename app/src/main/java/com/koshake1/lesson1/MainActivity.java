package com.koshake1.lesson1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonSelectCity = findViewById(R.id.buttonSelectCity);
        buttonSelectCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonSelectCityClicked();
            }
        });

        ImageButton buttonSettings = findViewById(R.id.imageButtonSettings);
        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonSettingsClicked();
            }
        });

    }

    private void onButtonSelectCityClicked() {
        startActivity(new Intent(this, CityActivity.class));
    }

    private void onButtonSettingsClicked() {
        startActivity(new Intent(this, SettingsActivity.class));
    }
}