package divine.calcify.com.divine;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

import divine.calcify.activities.CartActivity;
//import divine.calcify.com.divine.R;

/**
 * Created by filipp on 5/23/2016.
 */
public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService{

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("msg=",remoteMessage.getNotification().getBody());
        //showNotification(remoteMessage.getData().get("message"),remoteMessage.getNotification().getTitle());
        //remoteMessage.getData().get()..explore
        showNotification(remoteMessage.getNotification().getBody().toString(),remoteMessage.getNotification().getTitle());
    }

    private void showNotification(String message,String title) {
        Log.d("wht is msg=",message);
        Intent i = new Intent(this,CartActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentIntent(pendingIntent);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(0,builder.build());
    }


}
