package com.koshake1.lesson1.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.koshake1.lesson1.R;

public class SettingActivity extends AppCompatActivity {

    private static final String NameSharedPreference = "LOGIN";
    private static final String IsDarkTheme = "IS_DARK_THEME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

    }
}
