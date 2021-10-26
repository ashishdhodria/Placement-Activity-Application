package com.example.placementactivityapp;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Button_listener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //Cancel notification when user clicks it
        NotificationManager notificationManager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(intent.getExtras().getInt("id"));
        Toast.makeText(context,"Notification is clicked",Toast.LENGTH_LONG).show();
    }

}
