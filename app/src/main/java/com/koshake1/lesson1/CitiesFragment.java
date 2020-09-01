package com.koshake1.lesson1;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.koshake1.lesson1.Constants.CITY_RESULT;

public class CitiesFragment extends Fragment {

    final int RESULT_OK = 0;
    private String currentCity;
    private List<String> cities;

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

        final EditText editCity = getView().findViewById(R.id.editText);
        editCity.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    cities.add(editCity.getText().toString());
                    adapter.notifyItemInserted(cities.size() - 1);
                    editCity.setText("");
                    Toast.makeText(requireContext(), editCity.getText(), Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
        /*
        final Button buttonSpb = getView().findViewById(R.id.buttonSpb);

        buttonSpb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentResult = new Intent();
                currentCity = buttonSpb.getText().toString();
                intentResult.putExtra(CITY_RESULT, buttonSpb.getText().toString());

                if (getTargetFragment() != null) {
                    getTargetFragment().onActivityResult(getTargetRequestCode(), 0, intentResult);
                } else {
                    requireActivity().setResult(0, intentResult);
                    if (getResources().getConfiguration().orientation
                            != Configuration.ORIENTATION_LANDSCAPE) {
                        requireActivity().finish();
                    }
                }

            }
        });

        final Button buttonMsk = getView().findViewById(R.id.buttonMsk);
        buttonMsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentResult = new Intent();
                currentCity = buttonMsk.getText().toString();
                intentResult.putExtra(CITY_RESULT, currentCity);
                if (getTargetFragment() != null) {
                    getTargetFragment().onActivityResult(getTargetRequestCode(), 0, intentResult);
                } else {
                    requireActivity().setResult(0, intentResult);
                    if (getResources().getConfiguration().orientation
                            != Configuration.ORIENTATION_LANDSCAPE) {
                        requireActivity().finish();
                    }
                }
            }
        });

        final Button buttonNy = getView().findViewById(R.id.buttonNy);
        buttonNy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentResult = new Intent();
                currentCity = buttonNy.getText().toString();
                intentResult.putExtra(CITY_RESULT, currentCity);
                if (getTargetFragment() != null) {
                    getTargetFragment().onActivityResult(getTargetRequestCode(), 0, intentResult);
                } else {
                    requireActivity().setResult(0, intentResult);
                    if (getResources().getConfiguration().orientation
                            != Configuration.ORIENTATION_LANDSCAPE) {
                        requireActivity().finish();
                    }
                }
            }
        });
         */
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(CITY_RESULT, currentCity);
        super.onSaveInstanceState(outState);
    }
}