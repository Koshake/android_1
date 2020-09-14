package com.koshake1.lesson1.cities;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.koshake1.lesson1.R;
import com.koshake1.lesson1.data.HistoryParcel;
import com.koshake1.lesson1.temperature.TemperatureViewHolder;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryViewHolder> {

    private final List<HistoryParcel> data;
    private final Activity activity;
    private int menuPosition;

    public HistoryAdapter(List<HistoryParcel> data, Activity activity) {
        this.data = data;
        this.activity = activity;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        if (data != null) {
            holder.bind(String.valueOf(data.get(position).getCity()), data.get(position).getTemperature());
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    void addItem(HistoryParcel element) {
        data.add(element);
        notifyItemInserted(data.size() - 1);
    }

    void updateItem(HistoryParcel element, int position) {
        data.set(position, element);
        notifyItemChanged(position);
    }

    void removeItem(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    void clearItems() {
        data.clear();
        notifyDataSetChanged();
    }
    
    public int getMenuPosition() {
        return menuPosition;
    }

}
