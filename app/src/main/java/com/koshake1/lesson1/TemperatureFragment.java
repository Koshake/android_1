package com.koshake1.lesson1;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import static com.koshake1.lesson1.Constants.CITY_RESULT;

public class TemperatureFragment extends Fragment {

    private final static int REQUEST_CODE = 123;
    private final static int SETTING_CODE = 88;
    private final int MAX_HOURS = 24;
    private String currentCity;
    private boolean isLandscape;
    private List<Parcel> history;

    public static TemperatureFragment newInstance(String param1) {
        TemperatureFragment fragment = new TemperatureFragment();
        Bundle args = new Bundle();
        args.putString(CITY_RESULT, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentCity = getArguments().getString(CITY_RESULT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_temperature, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            currentCity = savedInstanceState.getString(CITY_RESULT, getResources().getString(R.string.saint_petersburg));
            TextView cityText = getView().findViewById(R.id.textViewCity);
            cityText.setText(currentCity);

        } else {
            currentCity = getResources().getString(R.string.saint_petersburg);
            history = new ArrayList<>();
            for (int i = 0; i < MAX_HOURS; i++) {
                Parcel parcel = new Parcel(i, getResources().getStringArray(R.array.temperature)[i]);
                history.add(parcel);
            }

            final RecyclerView recyclerView = getView().findViewById(R.id.recycleTemp);
            recyclerView.setLayoutManager(new LinearLayoutManager(getView().getContext(),
                    RecyclerView.HORIZONTAL, false));
            final TemperatureAdapter adapter = new TemperatureAdapter();
            recyclerView.setAdapter(adapter);
            adapter.setItems(history);
        }

        Button buttonSelectCity = getView().findViewById(R.id.buttonSelectCity);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            buttonSelectCity.setVisibility(View.GONE);
        } else {
            buttonSelectCity.setVisibility(View.VISIBLE);
        }
        buttonSelectCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonSelectCityClicked();
            }
        });

        ImageButton buttonSettings = getView().findViewById(R.id.imageButtonSettings);
        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonSettingsClicked();
            }
        });

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;

        if (isLandscape) {
            showCitiesFragment();
        }

    }

    private void onButtonSelectCityClicked() {
        if (!isLandscape) {
            showCitiesFragment();
        }
    }

    private void onButtonSettingsClicked() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), SettingActivity.class);
        startActivityForResult(intent, SETTING_CODE);
    }

    private void showCitiesFragment() {
        if (isLandscape) {
            CitiesFragment detail = (CitiesFragment)
                    getFragmentManager().findFragmentById(R.id.fragment_cities);
            if (detail == null) {
                detail = CitiesFragment.create(currentCity);
                detail.setTargetFragment(this, REQUEST_CODE);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_cities, detail);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        } else {
            Intent intent = new Intent();
            intent.setClass(getActivity(), CityActivity.class);
            intent.putExtra(CITY_RESULT, currentCity);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //Toast.makeText(requireContext(), "Got result", Toast.LENGTH_SHORT).show();
        if (requestCode != REQUEST_CODE) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
        if (requestCode == REQUEST_CODE) {
            TextView cityText = getView().findViewById(R.id.textViewCity);
            if (data != null && !data.getStringExtra(CITY_RESULT).isEmpty()) {
                currentCity = data.getStringExtra(CITY_RESULT);
                cityText.setText(currentCity);
                Snackbar.make(getView(), String.format("City changed to %s", currentCity), Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        }
    }
}