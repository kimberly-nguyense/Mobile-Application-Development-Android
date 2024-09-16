package com.example.d308vacationplanner.ui;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.d308vacationplanner.R;

public class MyReceiver extends BroadcastReceiver {
    String channel_id = "channel_id";
    static int notificationID = 1;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        createNotificationChannel(context, channel_id);
        Notification notification;
        notification = new NotificationCompat.Builder(context, channel_id)
                .setSmallIcon(R.drawable.baseline_houseboat_24)
                .setContentText(intent.getStringExtra("name") + " " + intent.getStringExtra("date"))
                .setContentTitle("Notification Test").build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID++, notification);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel(Context context, String CHANNEL_ID){
        CharSequence name = "Kim's Convenience";
        String description = "Kim's Convenience Notification Channel";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}