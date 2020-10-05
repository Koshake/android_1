package com.koshake1.lesson1.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.koshake1.lesson1.dao.CityHistoryDao;
import com.koshake1.lesson1.history.History;

@Database(entities = {History.class}, version = 1)
public abstract class CityHistoryDatabase extends RoomDatabase {
    public abstract CityHistoryDao getHistoryDao();
}
