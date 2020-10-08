package com.koshake1.lesson1.broadcast;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class myBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BATTERY_LOW)) {
            Toast.makeText(context, "Low battery", Toast.LENGTH_LONG).show();
        } else if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            boolean noConnectivity = intent.getBooleanExtra(
                    ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
            if (noConnectivity) {
                Toast.makeText(context, "No connection", Toast.LENGTH_LONG).show();
            }
        }
    }
}
