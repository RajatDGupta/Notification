package com.example.neeraj.myapplication;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Base64;
import android.widget.Toast;

import com.example.neeraj.myapplication.NotificationPackage.NotificationPojo;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Neeraj on 9/7/2017.
 */

public class FcmMessagingServices extends FirebaseMessagingService {
String title,message,icon,click_action;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

              title = remoteMessage.getNotification().getTitle();
              message = remoteMessage.getNotification().getBody();
             // icon = remoteMessage.getNotification().getIcon();
            //  click_action = remoteMessage.getNotification().getClickAction();

            Intent intent = new Intent(this, NotificationViewActivity.class);
            intent.putExtra("title", title);
            intent.putExtra("message", message);
            intent.putExtra("icon", icon);

            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
            localBroadcastManager.sendBroadcast(intent);


            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);
                   // .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(getBitmapFromURL(icon)));

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, notificationBuilder.build());

            insertNotification();


        }


    private void insertNotification() {
        ApiInterface apiInterface=ApiClient.getClient().create(ApiInterface.class);
        Call<NotificationPojo> notificationPojoCall=apiInterface.insert(title,message,icon);
        notificationPojoCall.enqueue(new Callback<NotificationPojo>() {
            @Override
            public void onResponse(Call<NotificationPojo> call, Response<NotificationPojo> response) {
                Toast.makeText(FcmMessagingServices.this, "Inserted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<NotificationPojo> call, Throwable t) {

            }
        });
    }





    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
