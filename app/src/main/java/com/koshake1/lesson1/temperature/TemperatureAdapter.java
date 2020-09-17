package com.koshake1.lesson1.temperature;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.koshake1.lesson1.data.ParcelHourTemp;
import com.koshake1.lesson1.R;

import java.util.List;

class TemperatureAdapter extends RecyclerView.Adapter<TemperatureViewHolder> {

    private List<ParcelHourTemp> items;

    @NonNull
    @Override
    public TemperatureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TemperatureViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_temperature, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull TemperatureViewHolder holder, int position) {
        if (items != null) {
            holder.bind(String.valueOf(items.get(position).getHour()), items.get(position).getTemperature());
        }
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        } else {
            return items.size();
        }
    }

    public void setItems(List<ParcelHourTemp> items) {
        this.items = items;
        notifyDataSetChanged();
    }
}
