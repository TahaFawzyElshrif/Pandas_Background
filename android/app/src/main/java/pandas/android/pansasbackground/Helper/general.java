package pandas.android.pansasbackground.Helper;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import pandas.android.pansasbackground.R;

public class general {
    public static String getRealPathFromURI(Context context, Uri contentUri) {
        if ("content".equals(contentUri.getScheme())) {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(contentUri, projection, null, null, null);
            if (cursor != null) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                String path = cursor.getString(columnIndex);
                cursor.close();
                return path;
            }
        }else if ("file".equals(contentUri.getScheme())) {
            return contentUri.getPath();  // Directly return the path for file URIs
        }
        return null; // Return null if cursor is null
    }
    public static String[] getJavaArray(String python_arr) {

        String[] words= python_arr.split(",");
        words[0]=words[0].substring(words[0].indexOf('[')+1);
        words[words.length-1]=words[words.length-1].substring(0,words[words.length-1].indexOf(']'));

        for(int i=0;i<words.length ;i++){
            words[i]=removeQuotes(words[i].strip());
        }
        return words;
    }
    private static String removeQuotes(String s){
        if (s.startsWith("\"")){//has "  at first
            //--> so must be after strip
            s=s.substring(s.indexOf("\"")+1);
        }
        if (s.endsWith("\"")){//has "  at end
            s=s.substring(0,s.lastIndexOf("\""));
        }
        if (s.startsWith("\'")){//has '  at first
            s=s.substring(s.indexOf("\'")+1);
        }
        if (s.endsWith("\'")){//has '  at end
            s=s.substring(0,s.lastIndexOf("\'"));
        }
        return s;
    }
    public static String getPythonArr(ArrayList<String> items){
        String output="[";
        for (int i=0 ;i<items.size();i++){
            output=output+"\""+items.get(i)+"\"";
            output=i==items.size()-1?output:output+",";
        }
        output=output+"]";
        return output;
    }
    public static AlertDialog prepareSmallAlertDialog(Context current_context, int drawable, int message , boolean cancelale) {
        AlertDialog.Builder builder = new AlertDialog.Builder(current_context);
        LayoutInflater inflater = LayoutInflater.from(current_context);
        View customLayout = inflater.inflate(R.layout.small_alert_activity, null);

        ImageView wait_image= customLayout.findViewById(R.id.load);
        Glide.with(current_context)
                .asGif()
                .load(drawable)
                .into(wait_image);
        TextView messageBox=customLayout.findViewById(R.id.message);
        messageBox.setText(message);
        builder.setView(customLayout);


        AlertDialog dialog=builder.create();
        dialog.setCancelable(cancelale);  // Disable back button cancel
        dialog.setCanceledOnTouchOutside(cancelale);  // Disable outside touch cancel

        dialog.show();
        return  dialog;
    }

}
