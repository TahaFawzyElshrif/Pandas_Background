package pandas.android.pansasbackground;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import pandas.android.pansasbackground.Classes.Notifications;
import pandas.android.pansasbackground.Helper.Utils_premession;
import pandas.android.pansasbackground.broadCastReciever.open_image_download_broadcast;

///////////////////////////// Limit - output on not clear image ?-readme



public class MainActivity extends AppCompatActivity {
    public static final int _requestcode_premessions = 0;
    public static String[] premessions = {//Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_VIDEO,
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.POST_NOTIFICATIONS,
            Manifest.permission.INTERNET,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.frameLayout2), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        add_premession();


        new Handler().postDelayed(() -> {
            goToFragmentPlace() ;
            finish();
        }, 2700);
    }

    private void goToFragmentPlace() {
        Intent go=new Intent(MainActivity.this,fragments_place.class);
        startActivity(go);
    }


    private void add_premession() {

        if (!Utils_premession.checkPremession(MainActivity.this, premessions)) {
            Toast.makeText(MainActivity.this, "premession not accepted ! please accept", Toast.LENGTH_LONG).show();

            ActivityCompat.requestPermissions(MainActivity.this, premessions, _requestcode_premessions);

        } //no need to do thing else
          //  Toast.makeText(MainActivity.this, "all premessions request accepted", Toast.LENGTH_LONG).show();


    }

    //  I understood this called on result of requesting premession
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case _requestcode_premessions: {

                if (!Utils_premession.checkPremession(MainActivity.this, premessions)) {
                    Toast.makeText(MainActivity.this, "premession failed"
                            , Toast.LENGTH_LONG).show();
                } else {
                    //ActivityCompat.requestPermissions(MainActivity.this,arr,_requestcode);
                    Toast.makeText(MainActivity.this, "premession request done ", Toast.LENGTH_LONG).show();

                }
                break;
            }
        }
    }


}