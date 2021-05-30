package com.example.questionare;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;


public class NotificationHelper {
    private static final String CHANNEL_ID = "questionare_channel";
    private final int NOTIFICATION_ID = 0;
    private NotificationManager nm;
    private Context context;

    public NotificationHelper(Context context) {
        this.context = context;
        this.nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        createChannel();
    }

    private void createChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Questionare Alert", NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("Alert for Questionare app");
        nm.createNotificationChannel(channel);
    }

    public void PushAlert(String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Questionare Alert")
            .setContentText(message)
            .setSmallIcon(R.drawable.common_full_open_on_phone);
        nm.notify(NOTIFICATION_ID, builder.build());
    }

    public void Cancel() {
        nm.cancel(NOTIFICATION_ID);
    }
}