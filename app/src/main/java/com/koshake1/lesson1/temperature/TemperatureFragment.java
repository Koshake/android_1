package com.koshake1.lesson1.temperature;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.koshake1.lesson1.App;
import com.koshake1.lesson1.BuildConfig;
import com.koshake1.lesson1.HistorySource;
import com.koshake1.lesson1.R;
import com.koshake1.lesson1.cities.CitiesFragment;
import com.koshake1.lesson1.cities.CityActivity;
import com.koshake1.lesson1.cities.HistoryActivity;
import com.koshake1.lesson1.dao.CityHistoryDao;
import com.koshake1.lesson1.data.HistoryParcel;
import com.koshake1.lesson1.data.ParcelHourTemp;
import com.koshake1.lesson1.dialog.MyBottomSheetDialogFragment;
import com.koshake1.lesson1.dialog.OnDialogListener;
import com.koshake1.lesson1.history.History;
import com.koshake1.lesson1.model.WeatherRequest;
import com.koshake1.lesson1.settings.SettingActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.koshake1.lesson1.data.Constants.CITY_RESULT;
import static com.koshake1.lesson1.data.Constants.KEY_CITY;

public class TemperatureFragment extends Fragment
        implements NavigationView.OnNavigationItemSelectedListener{

    private final static int REQUEST_CODE = 123;
    private final static int SETTING_CODE = 88;
    private final int MAX_HOURS = 24;
    private final float TEMP_OFFSET = 273.15f;
    public static final String HPARCEL = "Hparcel";
    public static final String WEATHER_PREF= "weather_pref";
    private String currentCity;
    private boolean isLandscape;
    private List<ParcelHourTemp> history;
    private HistoryParcel cityHistory;
    private TextView cityText;
    private TextView tempText;
    private TextView minTempText;
    private TextView maxTempText;
    private TextView description;
    WeatherRequestRetrofit retrofit;
    public static HistorySource historySource;

    private void init() {
        cityText = getView().findViewById(R.id.textViewCity);
        tempText = getView().findViewById(R.id.textViewTemp);
        minTempText = getView().findViewById(R.id.textViewMin);
        maxTempText = getView().findViewById(R.id.textViewMax);
        description = getView().findViewById(R.id.textViewDescription);

        retrofit = new WeatherRequestRetrofit(this);
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
        init();
        if (savedInstanceState != null) {
            currentCity = savedInstanceState.getString(CITY_RESULT, getResources().getString(R.string.moscow));
            cityText.setText(currentCity);

        } else {
            //currentCity = getResources().getString(R.string.moscow);
            loadSharedPreferences();
            history = new ArrayList<>();
            cityHistory = new HistoryParcel(currentCity, tempText.getText().toString());
            for (int i = 0; i < MAX_HOURS; i++) {
                ParcelHourTemp parcel = new ParcelHourTemp(i, getResources().getStringArray(R.array.temperature)[i]);
                history.add(parcel);
            }
            final RecyclerView recyclerView = getView().findViewById(R.id.recycleTemp);
            recyclerView.setLayoutManager(new LinearLayoutManager(getView().getContext(),
                    RecyclerView.HORIZONTAL, false));
            final TemperatureAdapter adapter = new TemperatureAdapter();
            recyclerView.setAdapter(adapter);
            adapter.setItems(history);

            initDataBase();
        }

        retrofit.initRetorfit();
        retrofit.requestRetrofit(currentCity, BuildConfig.WEATHER_API_KEY);

        Toolbar toolbar = initToolbar();
        initDrawer(toolbar);
    }

    private void initDataBase() {
        CityHistoryDao historyDao = App
                .getInstance()
                .getHistoryDao();
        historySource = new HistorySource(historyDao);
        //historySource.clearAll();
        History history = new History();
        history.city = currentCity;
        history.temp = tempText.getText().toString();
        historySource.addHistory(history);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Toast.makeText(requireContext(), "destroy", Toast.LENGTH_SHORT).show();
        saveSharedPreferences();
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
        intent.putExtra(HPARCEL, (Parcelable) cityHistory);
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

                retrofit.requestRetrofit(currentCity, BuildConfig.WEATHER_API_KEY);
                cityHistory = new HistoryParcel(currentCity, tempText.getText().toString());
                History history = new History();
                history.city = currentCity;
                history.temp = tempText.getText().toString();
                historySource.addHistory(history);
            }
        }
    }

    public void displayWeather(Response<WeatherRequest> response) {
        cityText.setText(response.body().getName());
        tempText.setText(String.format("%d\u00B0", Math.round(response.body().getMain().getTemp() - TEMP_OFFSET)));
        minTempText.setText(String.format("%d\u00B0", Math.round(response.body().getMain().getTemp_min() - TEMP_OFFSET)));
        maxTempText.setText(String.format("%d\u00B0", Math.round(response.body().getMain().getTemp_min() - TEMP_OFFSET)));
        description.setText(response.body().getWeather()[0].getDescription());
    }

    private final OnDialogListener dialogListener = new OnDialogListener() {
        @Override
        public void onDialogOk() {
            getActivity().finish();
        }

        @Override
        public void onDialogCancel() {

        }
    };

    public void setMessage(String text) {

        MyBottomSheetDialogFragment dialogFragment =
                MyBottomSheetDialogFragment.newInstance();
        dialogFragment.setOnDialogListener(dialogListener);
        dialogFragment.show(getFragmentManager(), "dialog_fragment");
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
   public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                onButtonSettingsClicked();
                return true;
            case R.id.action_select:
                showCitiesFragment();
                return true;
            case R.id.action_history:
                showHistoryFragment();
                return  true;
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

    public void changeImageOnTemperature(float temp)
    {
        String url;
        temp -= TEMP_OFFSET;
        if (temp < 0) {
            url = getResources().getString(R.string.winter_img);
        }
        else if (temp < 10) {
            url = getResources().getString(R.string.autumn_img);
        }
        else if (temp < 20) {
            url = getResources().getString(R.string.spring_img);
        }
        else {
            url = getResources().getString(R.string.summer_img);
        }

        setBackgroundImage(url);
    }

    private void setBackgroundImage(String url) {
        final ConstraintLayout layout = getView().findViewById(R.id.mainLayout);
        final ImageView img = new ImageView(getContext());
        Picasso.get().load(url)
                .into(img, new Callback() {
                    @Override
                    public void onSuccess() {
                        layout.setBackground(img.getDrawable());
                    }

                    @Override
                    public void onError(Exception e) {
                        layout.setBackground(getResources().getDrawable(R.drawable.sunset_sky));
                    }
                });
    }


    private void saveSharedPreferences() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences(WEATHER_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_CITY, currentCity);
        editor.commit();
    }

    private void loadSharedPreferences() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences(WEATHER_PREF, MODE_PRIVATE);
        currentCity = sharedPref.getString(KEY_CITY, getResources().getString(R.string.moscow));
    }
}



