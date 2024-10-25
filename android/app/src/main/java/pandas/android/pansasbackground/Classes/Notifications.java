package pandas.android.pansasbackground.Classes;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import pandas.android.pansasbackground.R;

public class Notifications {
    public static CharSequence name_upload_channel = "Upload_channel";
    public static String description_upload_channel = "Channel for use when uploading result";
    public static String channelId_upload_channel = "upload_channel";

    public static void sendSimpleNotification(Context context, String messageText, Class<?> reciever_class) {
        // Create a notification channel if targeting Android 8 (API 26) and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(channelId_upload_channel, name_upload_channel, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(description_upload_channel);

            // Register the channel with the system
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }

        }


        Intent intent = new Intent(context, reciever_class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT); // Wrap the intent in a PendingIntent

        // Create the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId_upload_channel)
                .setSmallIcon(R.drawable.panda)
                .setContentTitle("Pandas Background ")
                .setContentIntent(pendingIntent)
                .setContentText(messageText)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        // Send the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(1, builder.build());
            Log.d("TAG","checked");
        }else{
            Log.d("TAG","not checked");
        }

    }

}
