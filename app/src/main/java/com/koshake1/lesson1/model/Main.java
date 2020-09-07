package com.koshake1.lesson1.model;

public class Main {
    private float temp;
    private int pressure;
    private int humidity;
    private float temp_max;
    private float temp_min;

    public float getTemp() {
        return temp ;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public void setMinTemp(float minTemp) {
        this.temp_min = minTemp;
    }

    public void setMaxTemp(float maxTemp) {
        this.temp_min = maxTemp;
    }

    public float getTempMax() {
        return temp_max;
    }

    public float getTempMin() {
        return temp_min;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
}
