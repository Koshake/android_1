package com.koshake1.lesson1;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TemperatureViewHolder extends RecyclerView.ViewHolder{

    private final TextView hourView;
    private final TextView tempView;
    public TemperatureViewHolder(@NonNull View itemView) {
        super(itemView);
        hourView = itemView.findViewById(R.id.hourTextView);
        tempView = itemView.findViewById(R.id.tempTextView);
    }

    public void bind(final String hour, final  String temp) {
        hourView.setText(hour);
        tempView.setText(temp);
    }
}
