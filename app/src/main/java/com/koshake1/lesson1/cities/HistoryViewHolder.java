package com.koshake1.lesson1.cities;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.koshake1.lesson1.R;

public class HistoryViewHolder extends RecyclerView.ViewHolder {

    private final TextView cityView;
    private final TextView tempView;
    public HistoryViewHolder(@NonNull View itemView) {
        super(itemView);
        cityView = itemView.findViewById(R.id.cityHistoryTextView);
        tempView = itemView.findViewById(R.id.tempHistorytextView);
    }

    public void bind(final String hour, final  String temp) {
        cityView.setText(hour);
        tempView.setText(temp);
    }
}
