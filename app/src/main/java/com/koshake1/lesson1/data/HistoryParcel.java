package com.koshake1.lesson1.data;

import java.io.Serializable;

public class HistoryParcel implements Serializable {
    private String city;
    private String temperature;

    public String getCity() {
        return city;
    }

    public String getTemperature() {
        return temperature;
    }

    public HistoryParcel(String city, String temperature) {
        this.city = city;
        this.temperature = temperature;
    }
}
