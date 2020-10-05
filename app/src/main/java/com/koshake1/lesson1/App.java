package com.koshake1.lesson1;

import android.app.Application;

import androidx.room.Room;

import com.koshake1.lesson1.dao.CityHistoryDao;
import com.koshake1.lesson1.database.CityHistoryDatabase;


public class App extends Application {

    private static App instance;
    private CityHistoryDatabase db;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        db = Room.databaseBuilder(getApplicationContext(),
                CityHistoryDatabase.class,
                "history_database")
                .allowMainThreadQueries()
                .build();
    }

    public CityHistoryDao getHistoryDao() {
        return db.getHistoryDao();
    }
}
