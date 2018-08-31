package com.jdev.kolya.servies;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.jdev.kolya.R;
import com.jdev.kolya.utils.NotificationSystem;

import static android.support.constraint.Constraints.TAG;

/**
 * Created by Java_Dude on 8/30/2018.
 */

public class NotifyRecev extends FirebaseMessagingService {
    String body, title;
    NotificationSystem system;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        title = remoteMessage.getNotification().getTitle();
        body = remoteMessage.getNotification().getBody();
        system = new NotificationSystem(this,title,body,getString(R.string.default_notification_channel_id));
        system.notiff();



    }
}
