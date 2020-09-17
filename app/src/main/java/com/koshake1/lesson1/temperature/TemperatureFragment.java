package com.koshake1.lesson1.temperature;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.koshake1.lesson1.cities.HistoryActivity;
import com.koshake1.lesson1.data.HistoryParcel;
import com.koshake1.lesson1.data.Parcel;
import com.koshake1.lesson1.R;
import com.koshake1.lesson1.settings.SettingActivity;
import com.koshake1.lesson1.cities.CitiesFragment;
import com.koshake1.lesson1.cities.CityActivity;
import com.koshake1.lesson1.model.WeatherRequest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.koshake1.lesson1.data.Constants.CITY_RESULT;

public class TemperatureFragment extends Fragment
        implements NavigationView.OnNavigationItemSelectedListener{

    private final static int REQUEST_CODE = 123;
    private final static int SETTING_CODE = 88;
    private final int MAX_HOURS = 24;
    private final float TEMP_OFFSET = 273.15f;
    public static final String HPARCEL = "Hparcel";
    private String currentCity;
    private boolean isLandscape;
    private List<Parcel> history;
    private List<HistoryParcel> cityHistory;
    private TextView cityText;
    private TextView tempText;
    private TextView minTempText;
    private TextView maxTempText;
    private TextView description;

    WeatherHandler weatherHandler = new WeatherHandler(this);

    private static final String TAG = "WEATHER";

    private void init() {
        cityText = getView().findViewById(R.id.textViewCity);
        tempText = getView().findViewById(R.id.textViewTemp);
        minTempText = getView().findViewById(R.id.textViewMin);
        maxTempText = getView().findViewById(R.id.textViewMax);
        description = getView().findViewById(R.id.textViewDescription);
    }

    public String getCurrentCity() {
        return currentCity;
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
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_temperature, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            currentCity = savedInstanceState.getString(CITY_RESULT, getResources().getString(R.string.moscow));
            cityText.setText(currentCity);
        } else {
            init();
            currentCity = getResources().getString(R.string.moscow);
            history = new ArrayList<>();
            cityHistory = new ArrayList<>();
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


        weatherHandler.updateWeather();
        cityHistory.add(new HistoryParcel(currentCity, tempText.getText().toString()));

        Toolbar toolbar = initToolbar();
        initDrawer(toolbar);
    }

    private void initDrawer(Toolbar toolbar) {
        DrawerLayout drawer = getView().findViewById(R.id.drawer_layout);
        NavigationView navigationView = getView().findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
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

    private void showHistoryFragment() {
        Intent intent = new Intent();
        intent.putExtra(HPARCEL, (Serializable) cityHistory);
        intent.setClass(getActivity(), HistoryActivity.class);
        startActivity(intent);
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

                weatherHandler.updateWeather();

                cityHistory.add(new HistoryParcel(currentCity, tempText.getText().toString()));
            }
        }
    }

    public void displayWeather(WeatherRequest weatherRequest) {
        cityText.setText(weatherRequest.getName());
        tempText.setText(String.format("%d\u00B0", Math.round(weatherRequest.getMain().getTemp() - TEMP_OFFSET)));
        minTempText.setText(String.format("%d\u00B0", Math.round(weatherRequest.getMain().getTempMin() - TEMP_OFFSET)));
        maxTempText.setText(String.format("%d\u00B0", Math.round(weatherRequest.getMain().getTempMax() - TEMP_OFFSET)));
        description.setText(weatherRequest.getWeather()[0].getDescription());
    }

    public void setMessage(String text) {
        Snackbar.make(getView(), text, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
   public boolean onOptionsItemSelected(MenuItem item) {
       int id = item.getItemId();

       if (id == R.id.action_settings) {
           onButtonSettingsClicked();
           return true;
       }

       if (id == R.id.action_select) {
           showCitiesFragment();
           return true;
       }

        if (id == R.id.action_history) {
            showHistoryFragment();
            return true;
        }

       return super.onOptionsItemSelected(item);
   }

    private Toolbar initToolbar() {
        Toolbar toolbar = getView().findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        return toolbar;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_settings:
                onButtonSettingsClicked();
                return true;
            case R.id.nav_cities:
                showCitiesFragment();
                return true;
            case R.id.nav_history:
                showHistoryFragment();
                return  true;
        }
        return false;
    }


}



