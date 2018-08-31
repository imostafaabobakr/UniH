package com.jdev.kolya.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.jdev.kolya.R;

/**
 * Created by Java_Dude on 8/30/2018.
 */

public class NotificationSystem {
    Context conn;
    String title,postbody,id;


    public NotificationSystem(Context conn, String title, String postbody,String id) {
        this.conn = conn;
        this.title = title;
        this.postbody = postbody;
        this.id = id;

    }

    public void notiff()
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(conn,id);
        builder.setContentTitle(title)
                .setContentText(postbody)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setSmallIcon(R.mipmap.ic_launcher);
        int notif_id = (int) System.currentTimeMillis();
        NotificationManager manager = (NotificationManager) conn.getSystemService(conn.NOTIFICATION_SERVICE);
        manager.notify(notif_id,builder.build());
    }

}
