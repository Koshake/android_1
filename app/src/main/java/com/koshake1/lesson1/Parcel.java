package com.koshake1.lesson1;

import java.io.Serializable;

public class Parcel implements Serializable {
    private int hour;
    private String temperature;

    public int getHour() {
        return hour;
    }

    public String getTemperature() {
        return temperature;
    }

    public Parcel(int hour, String temperature) {
        this.hour = hour;
        this.temperature = temperature;
    }
}
