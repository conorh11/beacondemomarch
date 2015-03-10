package com.example.conor.beacon_demo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;



public class NotificationService extends Service {
    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void onStart(Intent intent, int startId)
    {
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationintent = new Intent(this, MyService.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationintent, 0);
        int icon =R.drawable.fypicon;
        String tickertext = "New Beacon Active";
        Notification notification = new Notification(icon, tickertext, 0);
        String contentTitle = "iTour";
        String contentText = "New Beacon Found! Unlock to View ";
        notification.setLatestEventInfo(this, contentTitle, contentText, pendingIntent);
        notificationManager.notify(123, notification);



    }
}
