package pandas.android.pansasbackground.Helper;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import pandas.android.pansasbackground.Classes.Notifications;
import pandas.android.pansasbackground.broadCastReciever.open_image_download_broadcast;


public class Image {

    public static String readBase64FromFile(String filePath) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            FileInputStream fis = new FileInputStream(filePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return stringBuilder.toString();
    }

    public static Bitmap decodeBase64ToBitmap(String base64String) {
        // Decode the base64 string to byte array
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        // Convert byte array to Bitmap
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }


    public static void downald_image_file(Context context, byte[] imageBytes) throws Exception {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "downloadedfile.png");
//context.getExternalFilesDir(null), "downloadedfile2.png");
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(imageBytes);
            fos.flush();
        }

        openImageFile(file,context);
        Notifications.sendSimpleNotification(context,"Change succeed ! \n saved at "+file.getAbsolutePath()+"(Downloads)", open_image_download_broadcast.class);
        Log.d("Downloaded", "File saved as: " + file.getAbsolutePath());

    }

    public static void openImageFile(File file,Context context) {
        // Create a Uri from the file
        Uri imageUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);

        // Create an Intent to view the image
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(imageUri, "image/png");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        // Start the activity to open the image
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Log.e("Open Image", "No application found to open the image.");
        }
    }
}
