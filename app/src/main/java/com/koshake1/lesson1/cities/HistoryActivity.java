package com.koshake1.lesson1.cities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.koshake1.lesson1.R;
import com.koshake1.lesson1.data.HistoryParcel;

import java.util.List;

import static com.koshake1.lesson1.temperature.TemperatureFragment.HPARCEL;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<HistoryParcel> parcel = (List<HistoryParcel>) getIntent().getExtras().getSerializable(HPARCEL);

        setContentView(R.layout.activity_history);
        Button doneButton = findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        final RecyclerView recyclerView = findViewById(R.id.recyclerHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final HistoryAdapter adapter = new HistoryAdapter(parcel, this);
        recyclerView.setAdapter(adapter);
    }
}
