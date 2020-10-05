package com.koshake1.lesson1;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.koshake1.lesson1.main.MainActivity;

public class MyFirebaseService extends FirebaseMessagingService {
    private int messageId = 0;
    public MyFirebaseService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("PushMessage", remoteMessage.getNotification().getBody());
        String title = remoteMessage.getNotification().getTitle();
        if (title == null){
            title = "Push Message";
        }
        String text = remoteMessage.getNotification().getBody();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "2")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(text);
        NotificationManager notificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(messageId++, builder.build());

    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d("PushMessage", "Token " + s);
    }
}
