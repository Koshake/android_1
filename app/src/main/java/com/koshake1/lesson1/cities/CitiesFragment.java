package com.koshake1.lesson1.cities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.koshake1.lesson1.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static com.koshake1.lesson1.data.Constants.CITY_RESULT;

public class CitiesFragment extends Fragment {

    final int RESULT_OK = 0;
    private String currentCity;
    private List<String> cities;
    private Pattern checkCity = Pattern.compile("^[A-Z][a-z]{2,}$");

    public static CitiesFragment create(String city) {
        CitiesFragment f = new CitiesFragment();
        Bundle args = new Bundle();
        args.putString(CITY_RESULT, city);
        f.setArguments(args);
        return f;
    }

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
        return inflater.inflate(R.layout.fragment_cities, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cities = new ArrayList<>();
        cities.addAll(Arrays.asList(getResources().getStringArray(R.array.cities)));
        final RecyclerView recyclerView = getView().findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getView().getContext()));
        final CitiesAdapter adapter = new CitiesAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setCities(cities);
        adapter.setOnCityClickListener(new CitiesAdapter.OnCityClickListener() {
            @Override
            public void onClicked(String city) {
                Intent intentResult = new Intent();
                currentCity = city;
                intentResult.putExtra(CITY_RESULT, currentCity);
                if (getTargetFragment() != null) {
                    getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, intentResult);
                } else {
                    requireActivity().setResult(Activity.RESULT_OK, intentResult);
                    if (getResources().getConfiguration().orientation
                            != Configuration.ORIENTATION_LANDSCAPE) {
                        requireActivity().finish();
                    }
                }

            }
        });

        final TextInputEditText editCity = getView().findViewById(R.id.inputCity);
        editCity.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if (validate(editCity, checkCity, getResources().getString(R.string.Error_city))) {
                        cities.add(editCity.getText().toString());
                        Snackbar.make(getView(), String.format("City %s added", editCity.getText().toString()), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        adapter.notifyItemInserted(cities.size() - 1);
                        editCity.setText("");
                    }

                    return true;
                }
                return false;
            }
        });

    }

    private boolean validate(TextView tv, Pattern check, String message){
        String value = tv.getText().toString();
        if (check.matcher(value).matches()) {
            hideError(tv);
            return true;
        }
        else {
            showError(tv, message);
            return false;
        }
    }

    private void showError(TextView view, String message) {
        view.setError(message);
    }

    private void hideError(TextView view) {
        view.setError(null);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(CITY_RESULT, currentCity);
        super.onSaveInstanceState(outState);
    }
}