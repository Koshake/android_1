package com.koshake1.lesson1.cities;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.koshake1.lesson1.R;

class CityViewHolder extends RecyclerView.ViewHolder {

    private final TextView cityView;
    public CityViewHolder(@NonNull View itemView) {
        super(itemView);
        cityView = itemView.findViewById(R.id.cityTextView);
    }

    public void bind(final String city, final CitiesAdapter.OnCityClickListener onCityClickListener) {
        cityView.setText(city);
        cityView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCityClickListener != null) {
                    onCityClickListener.onClicked(city);
                }
            }
        });
    }

}
