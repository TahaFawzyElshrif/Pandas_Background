package pandas.android.pansasbackground.Helper;

import android.content.pm.PackageManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import pandas.android.pansasbackground.MainActivity;

public class Utils_premession {

    public static boolean checkPremession(AppCompatActivity context,String[] premessions) {

        // return ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        //         && ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ;

        boolean ckeckedPremession = true;
        for (int i = 0; i < premessions.length; i++) {
            ckeckedPremession = ckeckedPremession && (ActivityCompat.checkSelfPermission(context, premessions[0]) == PackageManager.PERMISSION_GRANTED);
        }
        return ckeckedPremession;
    }
}
