package com.koshake1.lesson1.history;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {History.CITY, History.TEMPERATURE})})
public class History {

    public final static String ID = "id";
    public final static String CITY = "city";
    public final static String TEMPERATURE = "temperature";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    public long id;

    @ColumnInfo(name = CITY)
    public String city;

    @ColumnInfo(name = TEMPERATURE)
    public String temp;
}
