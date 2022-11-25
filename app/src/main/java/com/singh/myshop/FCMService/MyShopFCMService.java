package com.singh.myshop.FCMService;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyShopFCMService extends FirebaseMessagingService {

    final String LOG_DBG=getClass().getSimpleName();

    @Override
    public void onNewToken(String s) {
        Log.e("NEW_TOKEN:-    ", s);
        SharedPreferences FirstTimeCredentials = getSharedPreferences("FirstTimeCredentials", Context.MODE_PRIVATE);
        FirstTimeCredentials.edit().putString("storedFCMToken",s).apply();
    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e(LOG_DBG, "Msg: " + remoteMessage.getData().toString());
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody) {
        SharedPreferences sharedPref = getSharedPreferences("loginCredentials", Context.MODE_PRIVATE);
        final String storedEmailId=sharedPref.getString("storedEmailId", "");
        if(storedEmailId.equalsIgnoreCase("")){
            return;
        }
       // Intent intent = new Intent(this, NotificationActivity.class);
       // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
       // PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent,
              //  PendingIntent.FLAG_ONE_SHOT);

//        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            notificationBuilder.setSmallIcon(R.mipmap.ic_rb_notify);
//            notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_rb_notify));
//            notificationBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody));
//        } else {
//            notificationBuilder.setSmallIcon(R.mipmap.ic_rb_notify);
//        }
//        notificationBuilder.setContentTitle("RealBooks")       //RealBooks Data Sync
//                .setContentText(messageBody)
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        notificationManager.notify(0, notificationBuilder.build());
    }
}
