package com.koshake1.lesson1.data;

import android.os.Parcel;
import android.os.Parcelable;

public class ParcelHourTemp implements Parcelable {
    private int hour;
    private String temperature;

    public int getHour() {
        return hour;
    }

    public String getTemperature() {
        return temperature;
    }

    public ParcelHourTemp(int hour, String temperature) {
        this.hour = hour;
        this.temperature = temperature;
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(temperature);
        parcel.writeInt(hour);
    }

    public static final Parcelable.Creator<ParcelHourTemp> CREATOR = new Parcelable.Creator<ParcelHourTemp>() {
        public ParcelHourTemp createFromParcel(Parcel in) {
            return new ParcelHourTemp(in);
        }

        public ParcelHourTemp[] newArray(int size) {
            return new ParcelHourTemp[size];
        }
    };

    private ParcelHourTemp(Parcel parcel) {
        temperature = parcel.readString();
        hour = parcel.readInt();
    }
}
