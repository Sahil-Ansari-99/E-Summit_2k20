package com.company.e_summit;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;

public class NotificationService extends FirebaseMessagingService {
    private NotificationManager notificationManager;
    String notificationKey, notificationKeyValue, currSummit;

    @Override
    public void onNewToken(String s) {
        Log.e("NEW_TOKEN", s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setUpNotificationChannels();
        }

        SharedPreferences preferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
        if (preferences.contains("summit")) {
            currSummit = preferences.getString("summit", "youth summit");
        }

        int notificationId = new Random().nextInt(6000);
        Intent notificationIntent = new Intent(this, FirstActivity.class);

        for (Map.Entry<String, String> entry : remoteMessage.getData().entrySet()) {
            notificationKey = entry.getKey();
            notificationKeyValue = entry.getValue();
            System.out.println("Test notif: " + notificationKey + " " + notificationKeyValue);
        }

        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        if (notificationKeyValue.toLowerCase().equals(currSummit)) {
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_ONE_SHOT);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "default")
                    .setSmallIcon(R.mipmap.e_summit_logo)
                    .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.e_summit_logo))
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(remoteMessage.getNotification().getBody()))
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            notificationManager.notify(notificationId, notificationBuilder.build());
        }

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.e("newToken",newToken);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setUpNotificationChannels() {
        NotificationChannel adminChannel;
        adminChannel = new NotificationChannel("default", "default", NotificationManager.IMPORTANCE_LOW);
        adminChannel.setDescription("default");
        adminChannel.enableLights(true);
        adminChannel.setLightColor(Color.RED);
        adminChannel.enableVibration(true);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(adminChannel);
        }
    }
}
