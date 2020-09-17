package com.koshake1.lesson1.data;

import android.os.Parcel;
import android.os.Parcelable;

public class HistoryParcel implements Parcelable {
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

    public HistoryParcel(Parcel in) {
        String[] data = new String[2];
        in.readStringArray(data);
        city = data[0];
        temperature = data[1];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[] { city, temperature });
    }

    public static final Parcelable.Creator<HistoryParcel> CREATOR = new Parcelable.Creator<HistoryParcel>() {

        @Override
        public HistoryParcel createFromParcel(Parcel source) {
            return new HistoryParcel(source);
        }

        @Override
        public HistoryParcel[] newArray(int size) {
            return new HistoryParcel[size];
        }
    };
}
