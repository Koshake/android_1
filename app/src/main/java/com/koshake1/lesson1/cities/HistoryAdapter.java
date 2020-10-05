package com.koshake1.lesson1.cities;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.koshake1.lesson1.HistorySource;
import com.koshake1.lesson1.R;
import com.koshake1.lesson1.data.HistoryParcel;
import com.koshake1.lesson1.history.History;
import com.koshake1.lesson1.temperature.TemperatureViewHolder;

import java.util.List;

import static com.koshake1.lesson1.temperature.TemperatureFragment.historySource;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryViewHolder> {

    //private HistorySource historySource;
    private final Activity activity;
    private int menuPosition;

    public HistoryAdapter(Activity activity) {
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
        if (holder != null) {
            List<History> historyList = historySource.getHistory();
            History history = historyList.get(position);
            holder.bind(String.valueOf(history.city), String.valueOf(history.temp));
        }
    }

    @Override
    public int getItemCount() {
        return historySource == null ? 0 : (int) historySource.getCountHistory();
    }

    void addItem(History history) {
        historySource.addHistory(history);
        notifyItemInserted((int) historySource.getCountHistory() - 1);
    }

    void updateItem(History history, int position) {
        historySource.updateHistory(history);
        notifyItemChanged(position);
    }

    void removeItem(int position) {
        historySource.removeHistory(position);
        notifyItemRemoved(position);
    }
    
    public int getMenuPosition() {
        return menuPosition;
    }

}
