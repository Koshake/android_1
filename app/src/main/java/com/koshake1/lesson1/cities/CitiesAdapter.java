package com.koshake1.lesson1.cities;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.koshake1.lesson1.R;

import java.util.List;

class CitiesAdapter extends RecyclerView.Adapter<CityViewHolder> {

    private List<String> cities;
    private OnCityClickListener onCityClickListener;

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CityViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_city, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        if (cities != null) {
            holder.bind(cities.get(position), onCityClickListener);
        }
    }

    @Override
    public int getItemCount() {
        if (cities == null) {
            return 0;
        } else {
            return cities.size();
        }
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
        notifyDataSetChanged();
    }

    public void setOnCityClickListener(OnCityClickListener onCityClickListener) {
        this.onCityClickListener = onCityClickListener;
    }

    interface OnCityClickListener {
        void onClicked(String city);
    }
}
