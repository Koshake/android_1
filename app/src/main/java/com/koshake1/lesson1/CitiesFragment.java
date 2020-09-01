package com.koshake1.lesson1;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static com.koshake1.lesson1.Constants.CITY_RESULT;

public class CitiesFragment extends Fragment {

    private String currentCity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            currentCity = savedInstanceState.getString(CITY_RESULT, getResources().getString(R.string.saint_petersburg));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cities, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Button buttonSpb = getView().findViewById(R.id.buttonSpb);

        buttonSpb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentResult = new Intent();
                currentCity = buttonSpb.getText().toString();
                intentResult.putExtra(CITY_RESULT, buttonSpb.getText().toString());

            }
        });

        final Button buttonMsk = getView().findViewById(R.id.buttonMsk);
        buttonMsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentResult = new Intent();
                currentCity = buttonMsk.getText().toString();
                intentResult.putExtra(CITY_RESULT, currentCity);
            }
        });

        final Button buttonNy = getView().findViewById(R.id.buttonNy);
        buttonNy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentResult = new Intent();
                currentCity = buttonNy.getText().toString();
                intentResult.putExtra(CITY_RESULT, currentCity);
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(CITY_RESULT, currentCity);
        super.onSaveInstanceState(outState);
    }
}