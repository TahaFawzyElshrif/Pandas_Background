package pandas.android.pansasbackground.broadCastReciever;

// open_image_download_broadcast.java
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import pandas.android.pansasbackground.Helper.Utils_API;

public class open_image_download_broadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Saved at downloads!", Toast.LENGTH_SHORT).show();
    }
}
