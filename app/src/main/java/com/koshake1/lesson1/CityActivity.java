package com.koshake1.lesson1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CityActivity extends AppCompatActivity {

    private static final String WIND_SPEED = "WIND_SPEED";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        Toast.makeText(getApplicationContext(), "#CityActivity onCreate()", Toast.LENGTH_SHORT).show();
        Log.d("CityActivity", "#CityActivity onCreate()");

        final CityPresenter presenter = CityPresenter.getInstance();
        CheckBox checkBox = (CheckBox)findViewById(R.id.checkWind);
        checkBox.setChecked(presenter.isShowWindSpeed());

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CityActivity.this.onCheckWindClicked();
            }
        });
    }

    private void onCheckWindClicked() {
        final CityPresenter presenter = CityPresenter.getInstance();
        CheckBox checkBox = (CheckBox)findViewById(R.id.checkWind);
        presenter.setShowWindSpeed(checkBox.isChecked());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "#CityActivity onDestroy()", Toast.LENGTH_SHORT).show();
        Log.d("CityActivity", "#CityActivity onDestroy()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(getApplicationContext(), "#CityActivity onStart()", Toast.LENGTH_SHORT).show();
        Log.d("CityActivity", "#CityActivity onStart()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(getApplicationContext(), "#CityActivity onStop()", Toast.LENGTH_SHORT).show();
        Log.d("CityActivity", "#CityActivity onStop()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(getApplicationContext(), "#CityActivity onPause()", Toast.LENGTH_SHORT).show();
        Log.d("CityActivity", "#CityActivity onPause()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(getApplicationContext(), "#CityActivity onResume()", Toast.LENGTH_SHORT).show();
        Log.d("CityActivity", "#CityActivity onResume()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(getApplicationContext(), "#CityActivity onRestart()", Toast.LENGTH_SHORT).show();
        Log.d("CityActivity", "#CityActivity onRestart()");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        CheckBox checkBox = (CheckBox)findViewById(R.id.checkWind);
        outState.putBoolean(WIND_SPEED, checkBox.isChecked());
        Toast.makeText(getApplicationContext(), "#CityActivity onSaveInstantState()", Toast.LENGTH_SHORT).show();
        Log.d("CityActivity", "#CityActivity onSaveInstanceState()" + checkBox.isChecked());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        CheckBox checkBox = (CheckBox)findViewById(R.id.checkWind);
        boolean checked = savedInstanceState.getBoolean(WIND_SPEED, false);
        checkBox.setChecked(checked);
        Toast.makeText(getApplicationContext(), "#CityActivity onSaveRestoreState()", Toast.LENGTH_SHORT).show();
        Log.d("CityActivity", "#CityActivity onRestoreInstanceState() " + checked);
    }
}
